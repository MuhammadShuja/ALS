package com.alllinkshare.liveTrack.tracking.Model;

public class SecretModel {
   /* {
        "grant_type": "password",
            "client_id": "4",
            "client_secret": "Jo2fT2PP5YgTcnnWIWElI0U3aC6Eqlgr5QgCMWZh",
            "scope": "*",
            "username": "test@gmail.com",
            "password": "123456"
    }*/

    public String grant_type;
    public String client_id;
    public String client_secret;
    public String scope;
    public String username;
    public String password;
    public String user_type;

    public SecretModel(String grant_type, String client_id, String client_secret, String scope, String username, String password,String user_type) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.scope = scope;
        this.username = username;
        this.password = password;
        this.user_type = user_type;
    }
}
