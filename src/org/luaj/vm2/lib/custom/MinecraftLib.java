package org.luaj.vm2.lib.custom;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.*;
import ru.tutorialclient.util.font.Fonts;
import ru.tutorialclient.util.math.GCDUtil;
import ru.tutorialclient.util.misc.HudUtil;
import ru.tutorialclient.util.render.ColorUtil;
import ru.tutorialclient.util.render.RenderUtil;

public class MinecraftLib extends TwoArgFunction {
    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("getFPS", new fps());
        library.set("getPing", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                return LuaValue.valueOf(HudUtil.calculatePing());
            }
        });
        library.set("getServerIP", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                return LuaValue.valueOf(HudUtil.serverIP());
            }
        });
        library.set("getBPS", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                return LuaValue.valueOf(HudUtil.calculateBPS());
            }
        });
        library.set("getFixSensivity", new OneArgFunction() {
            @Override
            public LuaValue call(LuaValue arg) {
                return LuaValue.valueOf(GCDUtil.getSensitivity(arg.tofloat()));
            }
        });
        env.set("minecraft", library);
        return library;
    }

    static class fps extends ZeroArgFunction {
        @Override
        public LuaValue call() {
            return LuaValue.valueOf(Minecraft.debugFPS);
        }
    }

}
