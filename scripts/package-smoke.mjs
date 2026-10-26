import { mkdir, writeFile } from 'node:fs/promises';
import { fileURLToPath } from 'node:url';
import { execFileSync } from 'node:child_process';
const root=new URL('../',import.meta.url), consumer=new URL('.checks/consumer/',root);
await mkdir(new URL('src/test/java/local/',consumer),{recursive:true});
await writeFile(new URL('pom.xml',consumer),`<project xmlns="http://maven.apache.org/POM/4.0.0"><modelVersion>4.0.0</modelVersion><groupId>local.smoke</groupId><artifactId>consumer</artifactId><version>1</version><properties><maven.compiler.release>17</maven.compiler.release><project.build.sourceEncoding>UTF-8</project.build.sourceEncoding></properties><dependencies><dependency><groupId>com.smspva</groupId><artifactId>smspva-sdk</artifactId><version>0.1.0-alpha.1</version></dependency><dependency><groupId>org.junit.jupiter</groupId><artifactId>junit-jupiter</artifactId><version>5.13.4</version><scope>test</scope></dependency></dependencies><build><plugins><plugin><groupId>org.apache.maven.plugins</groupId><artifactId>maven-compiler-plugin</artifactId><version>3.16.0</version></plugin><plugin><groupId>org.apache.maven.plugins</groupId><artifactId>maven-surefire-plugin</artifactId><version>3.5.4</version></plugin></plugins></build></project>`);
await writeFile(new URL('src/test/java/local/ConsumerTest.java',consumer),`package local;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.smspva.sdk.*;
import java.time.Duration;
import java.util.Map;
public class ConsumerTest {
 @Test void installedJarWorksWithoutSdkSource() throws Exception {
  assertTrue(SmsPva.class.getProtectionDomain().getCodeSource().getLocation().toString().endsWith(".jar"));
  try(var contract=SmsPva.class.getResourceAsStream("contracts.json")) { assertNotNull(contract); }
  HttpTransport http=request -> new HttpTransport.Response(200, request.uri().getPath().startsWith("/activation") ? "{\\"statusCode\\":200,\\"data\\":{\\"balance\\":\\"10.50\\"}}" : "{\\"status\\":1,\\"data\\":[]}");
  try(SmsPva client=new SmsPva("local-key",Duration.ofSeconds(1),http,Map.of())) {
   assertEquals("10.50",client.activation().getBalance().path("data").path("balance").asText());
   assertEquals(1,client.rental().getCountries().path("status").asInt());
   assertEquals(1,client.email().getProviders().path("status").asInt());
   client.email().getData(new Requests.EmailGetData(null));
  }
 }
}`);
const executable=process.env.MAVEN_COMMAND ?? (process.platform==='win32'?'mvn.cmd':'mvn');
const args=['-B','-ntp','-f',fileURLToPath(new URL('pom.xml',consumer)),`-Dmaven.repo.local=${(process.env.MAVEN_REPO_LOCAL ?? fileURLToPath(new URL('.checks/m2',root)))}`,'test'];
if(process.platform==='win32') {
  const literal=value=>"'"+value.replaceAll("'","''")+"'";
  execFileSync('powershell.exe',['-NoProfile','-NonInteractive','-Command',`& ${literal(executable)} ${args.map(literal).join(' ')}; exit $LASTEXITCODE`],{stdio:'inherit',windowsHide:true});
}
else execFileSync(executable,args,{stdio:'inherit',windowsHide:true});
