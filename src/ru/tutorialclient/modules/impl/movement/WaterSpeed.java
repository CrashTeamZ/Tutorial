package ru.tutorialclient.modules.impl.movement;

import com.ibm.icu.impl.Utility;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Pose;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.MathHelper;
import ru.tutorialclient.events.Event;
import ru.tutorialclient.events.impl.player.*;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.FunctionAnnotation;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.modules.settings.imp.BooleanOption;
import ru.tutorialclient.modules.settings.imp.SliderSetting;
import ru.tutorialclient.util.movement.MoveUtil;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author dedinside
 * @since 22.07.2023
 */
@FunctionAnnotation(name = "WaterSpeed", type = Type.Movement)
public class WaterSpeed extends Function {


    public SliderSetting speed = new SliderSetting("Скорость", 0.41f, 0.1f, 0.5f, 0.01f);
    public SliderSetting motionY = new SliderSetting("Скорость по Y", 0.0f, 0.0f, 0.1f, 0.01f);

    public WaterSpeed() {
        addSettings(speed, motionY);
    }

    private float currentValue;

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventTravel move) {
            if (mc.player.collidedVertically || mc.player.collidedHorizontally) {
                return;
            }
            if (mc.player.isSwimming()) {
                float speed = this.speed.getValue().floatValue() / 10F;
                if (mc.gameSettings.keyBindJump.pressed) {
                    mc.player.motion.y += motionY.getValue().floatValue();
                }
                if (mc.gameSettings.keyBindSneak.pressed) {
                    mc.player.motion.y -= motionY.getValue().floatValue();
                }

                MoveUtil.setMotion(MoveUtil.getMotion());
                move.speed += speed;
            }
        }
    }

    public float calculateNewValue(float value, float increment) {
        return value * Math.min((currentValue += increment) / 100.0f, 1.0f);
    }

}
