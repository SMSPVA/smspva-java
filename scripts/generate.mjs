import { readFile, writeFile, mkdir } from 'node:fs/promises';
const root = new URL('../', import.meta.url);
const manifest = JSON.parse(await readFile(new URL('../src/main/resources/com/smspva/sdk/contracts.json', import.meta.url), 'utf8'));
const source = new URL('src/main/java/com/smspva/sdk/', root);
await mkdir(source, {recursive:true});
await mkdir(new URL('src/main/resources/com/smspva/sdk/', root), {recursive:true});
await mkdir(new URL('src/test/resources/', root), {recursive:true});
const pascal = x => x[0].toUpperCase()+x.slice(1);
let requests = 'package com.smspva.sdk;\n\nimport java.util.Map;\nimport java.util.LinkedHashMap;\n\n/** Generated typed request values. Null optional values are omitted; API names are preserved. */\npublic final class Requests {\n    private Requests() {}\n    public interface Params { Map<String,Object> toMap(); }\n';
for (const family of ['activation','rental','email']) {
  const cls = pascal(family);
  let resource = `package com.smspva.sdk;\n\nimport com.fasterxml.jackson.databind.JsonNode;\nimport java.util.Map;\n\n/** Generated named API methods. */\npublic final class ${cls} {\n    private final SmsPva transport;\n    ${cls}(SmsPva transport) { this.transport=transport; }\n`;
  for (const [key, op] of Object.entries(manifest.operations).filter(([,v])=>v.family===family)) {
    const req = cls+pascal(op.jsName);
    if (op.params.length) {
      const fields = [...op.params].sort((a,b)=>Number(b.required)-Number(a.required)).map(p=>`${p.schema.type==='integer'?'Long':'String'} ${p.name}`).join(', ');
      requests += `    public record ${req}(${fields}) implements Params {\n        public Map<String,Object> toMap() {\n            Map<String,Object> values=new LinkedHashMap<>();\n${op.params.map(p=>`            values.put(${JSON.stringify(p.name)}, ${p.name});`).join('\n')}\n            return values;\n        }\n    }\n`;
      resource += `    public JsonNode ${op.jsName}(Requests.${req} params) { return ${op.jsName}(params, RequestOptions.DEFAULT); }\n    public JsonNode ${op.jsName}(Requests.${req} params, RequestOptions options) {\n        if(params==null) throw new ValidationException("Request parameters must not be null");\n        return transport.request(${JSON.stringify(key)}, params.toMap(), options);\n    }\n`;
      if (!op.params.some(p=>p.required)) resource+=`    public JsonNode ${op.jsName}() { return transport.request(${JSON.stringify(key)}, Map.of(), RequestOptions.DEFAULT); }\n`;
    } else resource += `    public JsonNode ${op.jsName}() { return ${op.jsName}(RequestOptions.DEFAULT); }\n    public JsonNode ${op.jsName}(RequestOptions options) { return transport.request(${JSON.stringify(key)}, Map.of(), options); }\n`;
  }
  resource+='}\n';
  await writeFile(new URL(`${cls}.java`,source),resource);
}
requests+='}\n';
await writeFile(new URL('Requests.java',source),requests);
await writeFile(new URL('src/main/resources/com/smspva/sdk/contracts.json',root),JSON.stringify(manifest,null,2)+'\n');
await writeFile(new URL('src/test/resources/cases.json',root),await readFile(new URL('../src/test/resources/cases.json',import.meta.url)));
console.log('Generated Java request records and all 56 named methods.');
