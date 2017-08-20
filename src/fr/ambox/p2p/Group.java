package fr.ambox.p2p;

import fr.ambox.p2p.configuration.ConfigurationService;
import fr.ambox.p2p.configuration.IdentityService;

public class Group {
    private App[] apps;

    public Group(int size) {
        this.apps = new App[size];
        for (int i=0; i<size; i++) {
            App app = new App("app"+i);
            IdentityService identity = (IdentityService) app.getService("identity");
            identity.getMyId().setNickname("nick_app"+i);
            ConfigurationService configuration = (ConfigurationService) app.getService("configuration");
            configuration.setOption("routerPort", String.valueOf(10000 + (i*100)));
            configuration.setOption("httpPort", String.valueOf(10000 + (i*100) + 80));
            this.apps[i] = app;
        }
    }

    public void start() {
        for (App app : apps) {
            app.start();
        }
    }

    public App get(int index) {
        return this.apps[index];
    }
}
