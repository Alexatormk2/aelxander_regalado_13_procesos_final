package firma_digital;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
public class generar {


        public static void main(String[] args) {
            try {
                // Get instance and initialize a KeyPairGenerator object.
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
                keyGen.initialize(1024, random);

                // Get a PrivateKey from the generated key pair.
                KeyPair keyPair = keyGen.generateKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();

                // Get an instance of Signature object and initialize it.
                Signature signature = Signature.getInstance("SHA1withRSA");
                signature.initSign(privateKey);

                // Supply the data to be signed to the Signature object
                // using the update() method and generate the digital
                // signature.
                byte[] bytes = Files.readAllBytes(Paths.get("readme.txt"));
                signature.update(bytes);
                byte[] digitalSignature = signature.sign();

                // Save digital signature and the public key to a file.
                Files.write(Paths.get("signature"), digitalSignature);
                Files.write(Paths.get("publickey"), keyPair.getPublic().getEncoded());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

}
