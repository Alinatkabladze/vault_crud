import Models.User;
import Steps.Secrets;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;

public class Examples {

    Secrets secrets=new Secrets();
    @Test
    public void getCredentialsFromSecret(){
        User user=secrets.getCredentials("/secret/data/user1","Vault_Token");
        System.out.println(user.getlogin());
    }
    @Test
    public void getCredentialsFromKV(){
        User user=secrets.getCredentials("/kv/data/user","Vault_Token");
        System.out.println(user.getlogin());
    }

    @Test
    public void updateSecret(){
        String rndPassword = RandomStringUtils.randomAlphabetic(10);
        User user = new User("tstaut",rndPassword);
        secrets.createOrUpdateCredentials("/secret/data/user","Vault-Token",user);
        //for updating secret put an existing secret in the secretPath
        //for creating secret put new secret in the secretPath
    }
    @Test
    public void deleteSecret(){
        secrets.deleteCredentials("/secret/data/user","Vault-Token");
    }

}
