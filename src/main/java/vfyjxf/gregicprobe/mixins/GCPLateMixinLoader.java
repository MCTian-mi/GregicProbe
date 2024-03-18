package vfyjxf.gregicprobe.mixins;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Arrays;
import java.util.List;

public class GCPLateMixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        String[] configs = {"mixins.gregicprobe.late.json"};
        return Arrays.asList(configs);
    }
}
