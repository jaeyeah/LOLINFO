package com.lol.lolinfo.configuration;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import jakarta.annotation.PostConstruct;

@Profile("prod")
@Configuration
public class OracleWalletInitializer {

    @PostConstruct
    public void initWallet() throws Exception {
        String b64 = System.getenv("ORACLE_WALLET_BASE64");
        if (b64 == null || b64.isBlank()) {
            throw new RuntimeException("ORACLE_WALLET_BASE64 is missing");
        }

        // Render 컨테이너에서 쓸 wallet 디렉터리
        Path walletDir = Paths.get("/tmp/wallet");
        Files.createDirectories(walletDir);

        byte[] zipBytes = Base64.getMimeDecoder().decode(b64);

        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipBytes))) {
            ZipEntry e;
            while ((e = zis.getNextEntry()) != null) {
                Path out = walletDir.resolve(e.getName());
                // zip 내부에 폴더 엔트리 있을 수 있으니 디렉터리 생성
                if (e.isDirectory()) {
                    Files.createDirectories(out);
                } else {
                    Files.createDirectories(out.getParent());
                    Files.copy(zis, out, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }

        //  lolinfo_tp를 alias로 인식
        System.setProperty("oracle.net.tns_admin", walletDir.toString());
        System.setProperty("TNS_ADMIN", walletDir.toString());

        // sqlnet.ora의 DIRECTORY 경로를 /tmp/wallet로 강제 치환
        Path sqlnet = walletDir.resolve("sqlnet.ora");
        if (Files.exists(sqlnet)) {
            String s = Files.readString(sqlnet);
            s = s.replace("?/network/admin", walletDir.toString());
            Files.writeString(sqlnet, s);
        }
    }
}