package mods.bwf.proxy;

public interface Proxy {

    void preInit();

    void init();

    void postInit();

    void afterInit();
}
