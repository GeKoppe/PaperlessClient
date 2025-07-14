package org.dmsextension.paperless.client;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class PaperlessInterceptor implements Interceptor {
    private final Logger logger = LoggerFactory.getLogger(PaperlessInterceptor.class);
    private String user;
    private String pw;
    private String baseUrl;
    private static final HashMap<String, String> tokens = new HashMap<>();
    public PaperlessInterceptor(String user, String pw, String baseUrl) {
        this.pw = pw;
        this.user = user;
        this.baseUrl = baseUrl;
    }

    private static class Token {
        public String token;
    }
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (tokens.get(this.user) == null) {
            String json = String.format("{ \"username\": \"%s\",\"password\": \"%s\" }",
                    this.user,
                    this.pw
            );
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(
                            this.baseUrl + "token/"
                    )
                    .header("Content-Type", "application/json")
                    .post(RequestBody.create(json, MediaType.parse("application/json")))
                    .build();

            try (Response r = client.newCall(request).execute()) {
                if (r.isSuccessful()) {
                    logger.debug(String.format("Request successful, getting token for user %s", this.user));
                    JsonAdapter<Token> adapter = (new Moshi.Builder().build()).adapter(Token.class);
                    tokens.put(this.user, Objects.requireNonNull(adapter.fromJson(r.body().string())).token);
                } else {
                    this.logger.info("Could not get api token");
                    throw new IOException("API token not fetched");
                }
            } catch (Exception ex) {
                this.logger.info("Exception in api call: " + ex);
                throw ex;
            }
        }
        Request req = chain.request()
                .newBuilder()
                .header("Authorization", "Token " + tokens.get(this.user))
                .build();
        return chain.proceed(req);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
