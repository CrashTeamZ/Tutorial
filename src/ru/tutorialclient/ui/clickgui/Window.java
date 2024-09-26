package ru.tutorialclient.ui.clickgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.ui.clickgui.objects.ModuleObject;
import ru.tutorialclient.ui.clickgui.objects.Object;
import ru.tutorialclient.ui.clickgui.theme.ThemeDrawing;
import ru.tutorialclient.util.animations.Animation;
import ru.tutorialclient.util.animations.Direction;
import ru.tutorialclient.util.animations.impl.EaseBackIn;
import ru.tutorialclient.util.render.*;
import ru.tutorialclient.util.render.animation.AnimationMath;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ru.tutorialclient.util.IMinecraft.mc;

public class Window extends Screen {

    private Vector2f position = new Vector2f(0, 0);

    public static Vector2f size = new Vector2f(500, 400);

    public static int dark = new Color(18, 19, 25).getRGB();
    public static int medium = new Color(18, 19, 25).brighter().getRGB();
    public static int light = new Color(129, 134, 153).getRGB();
    //private final ThemeDrawing themeDrawing = new ThemeDrawing();
    private Type currentCategory;


    public static ArrayList<ModuleObject> objects = new ArrayList<>();

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
//        scrolling += (float) (delta * 30);
        for(Panel p:panels){
            p.onScroll(mouseX,mouseY,delta);
        }
        return super.mouseScrolled(mouseX, mouseY, delta);
    }

    public Window(ITextComponent titleIn) {
        super(titleIn);
        scrolling = 0;
        for (Function function : Managment.FUNCTION_MANAGER.getFunctions()) {
            objects.add(new ModuleObject(function));
        }
        size = new Vector2f(450, 350);
        position = new Vector2f(mc.getMainWindow().scaledWidth() / 2f, mc.getMainWindow().scaledHeight() / 2f);
        float offset = 0;
        float width = 120;
        for(Type type:Type.values()){
            panels.add(new Panel(type,(mc.getMainWindow().scaledWidth() / 2f)+offset, mc.getMainWindow().scaledHeight() / 2f,width,300));
            offset+=width+3;
        }

    }

    ArrayList<Panel> panels = new ArrayList<>();

    @Override
    protected void init() {
        super.init();
        panels.clear();
        size = new Vector2f(450, 350);
        float offset = 0;
        float width = 120;
        float height = 300;
        position = new Vector2f(mc.getMainWindow().scaledWidth() / 2f-(Type.values().length*width)/2f, (mc.getMainWindow().scaledHeight() / 2f)-height/2f);
        for(Type type:Type.values()){
            panels.add(new Panel(type,position.x+offset, position.y, width,height));
            offset+=width+3;
        }
    }

    public static float scrolling;
    public static float scrollingOut;

    public boolean searching;
    public static String searchText = "";
    public static boolean openAnimation=false;
    //    public static float animation;
    public Animation animation = new EaseBackIn(400, 1, 1.5f);

    @Override
    public boolean isPauseScreen() {
        return false;
    }



    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        GL11.glPushMatrix();
        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();
        if (Managment.FUNCTION_MANAGER.clickGui.blur.get()) {
            GaussianBlur.startBlur();
            RenderUtil.Render2D.drawRect(0, 0, width, height, -1);
            GaussianBlur.endBlur(Managment.FUNCTION_MANAGER.clickGui.blurVal.getValue().floatValue(), 1);
        }
        float bar = 100;
        mc.gameRenderer.setupOverlayRendering(2);

        float len;
        int id=0;
        if(openAnimation)animation.setDirection(Direction.FORWARDS);
        else animation.setDirection(Direction.BACKWARDS);
        GL11.glPushMatrix();

        for(Panel p:panels){
            p.render(mouseX,mouseY);
        }
        GL11.glPopMatrix();
        if(animation.getOutput()<0.1f&&!openAnimation){

            openAnimation = true;
        }
        // TODO: “»œ¿ ŒÕŒ ¡”ƒ≈“ –≈Õƒ≈–»“‹ “≈Ã€ ÕŒ Õ≈ ¬ —¬Œ≈…  ¿“≈√Œ–»» ¿ ¬≈«ƒ≈ ƒ¿ ≈—“ ∆≈
        // TODO:¿ ◊“Œ¡€ ¬ —¬Œ≈… Õ¿ƒŒ “»œ¿         if (currentCategory == Type.Configs) { ÚÛÚ‡ ÂÌ‰Â}  Ò‰ÂÎ‡Ú¸ ‰‡
      //  themeDrawing.draw(matrixStack, mouseX, mouseY, position.x + 100, position.y, size.x - bar, size.y);
        BloomHelper.draw(25, 1.5f, false);

        scrollingOut = AnimationMath.fast(scrollingOut, scrolling, 15);
        StencilUtil.initStencilToWrite();
        RenderUtil.Render2D.drawRoundedCorner(position.x, position.y, size.x, size.y, new Vector4f(8.5f, 8.5f, 8.5f, 8.5f), -1);
        StencilUtil.readStencilBuffer(0);
        StencilUtil.uninitStencilBuffer();
        mc.gameRenderer.setupOverlayRendering();
        GL11.glPopMatrix();
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (searching && searchText.length() < 13) {
            searchText += codePoint;
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for(Panel p:panels){
            p.onKey(keyCode,scanCode,modifiers);
        }
        if(keyCode == 256){
            mc.displayGuiScreen(null);
            openAnimation = false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }


    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for(Panel p:panels){
            p.onRelease(mouseX,mouseY,button);
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        super.onClose();
        searching = false;
        openAnimation=false;
        for (ModuleObject m : objects) {
            m.exit();
        }
    }

    public void drawObjects(MatrixStack stack, int mouseX, int mouseY) {
        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();
        List<ModuleObject> first = objects.stream()
                .filter(moduleObject -> (!searchText.isEmpty()) || moduleObject.function.category == currentCategory)
                .filter(moduleObject -> objects.indexOf(moduleObject) % 2 == 0)
                .toList();

        List<ModuleObject> second = objects.stream()
                .filter(moduleObject -> (!searchText.isEmpty()) || moduleObject.function.category == currentCategory)
                .filter(moduleObject -> objects.indexOf(moduleObject) % 2 != 0)
                .toList();


        RenderUtil.SmartScissor.push();
        RenderUtil.SmartScissor.setFromComponentCoordinates(position.x, position.y, size.x, size.y - 1);
        float offset = scrollingOut;
        float sizePanel = 0;
        for (ModuleObject object : first) {
            if (!searchText.isEmpty())
                if (!object.function.name.toLowerCase().contains(searchText.toLowerCase())) continue;
            object.x = position.x + 110;
            object.y = position.y + 10 + offset;
            object.width = 160;
            object.height = 22;
            for (Object object1 : object.object) {
                if (object1.setting.visible()) {
                    object.height += object1.height;
                }
            }
            object.height += 3;
//            if (!(object.y - object.height - 50 > size.y))
            object.draw(stack, mouseX, mouseY);
            offset += object.height += 5;
            sizePanel += object.height += 5;
        }
        float sizePanel1 = 0;
        offset = scrollingOut;
        for (ModuleObject object : second) {
            if (!searchText.isEmpty())
                if (!object.function.name.toLowerCase().contains(searchText.toLowerCase())) continue;
            object.x = position.x + 280;
            object.y = position.y + 10 + offset;
            object.width = 160;
            object.height = 22;
            for (Object object1 : object.object) {
                if (object1.setting.visible()) {
                    object.height += object1.height;
                }
            }
            object.height += 3;
///            if (!(object.y - object.height - 50 > size.y))
            object.draw(stack, mouseX, mouseY);
            offset += object.height += 5;
            sizePanel1 += object.height += 5;
        }
        BloomHelper.draw(6, 1.5f, false);
        RenderUtil.SmartScissor.unset();
        RenderUtil.SmartScissor.pop();
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Vec2i fixed = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = fixed.getX();
        mouseY = fixed.getY();

        //TODO:  ¿ “”“¿ Ã€ “»œ¿ —ƒ≈À¿À» ◊“Œ¡€ ŒÕŒ “¿ ∆≈ ¬»«ƒ≈ –Œ¡Œ“¿ÀŒ ƒ¿       ¿ ›“Œ ƒÀﬂ “Œ√Œ ◊“Œ¡€ –Œ¡Œ“¿ÀŒ “ŒÀ‹ Œ ¬ “≈Ã≈ ƒ¿ if (currentCategory == Type.Theme) {}
       // themeDrawing.click((int) mouseX, (int) mouseY, button);

        float bar = 100;

        float len = 0;
        for (Type type : Type.values()) {
            len = type.ordinal() * 20;

            if (RenderUtil.isInRegion(mouseX, mouseY, position.x + 5f, position.y + 55 + len, bar - 10, 15)) {
                currentCategory = type;
                searching = false;
            }
        }

        for (ModuleObject m : objects) {
            if (searching || !searchText.isEmpty()) {
                if (!searchText.isEmpty())
                    if (!m.function.name.toLowerCase().contains(searchText.toLowerCase())) continue;
                m.mouseClicked((int) mouseX, (int) mouseY, button);
            } else {
                if (m.function.category == currentCategory)
                    m.mouseClicked((int) mouseX, (int) mouseY, button);
            }

        }


        if (RenderUtil.isInRegion(mouseX, mouseY, position.x + 7.5f, position.y + 30, bar - 15, 17)) {
            searching = !searching;
        }



        for(Panel p:panels){
            p.onClick(mouseX,mouseY,button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
