package ru.tutorialclient.ui.clickgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import org.joml.Vector4i;
import org.lwjgl.opengl.GL11;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.modules.Function;
import ru.tutorialclient.modules.Type;
import ru.tutorialclient.ui.clickgui.configs.ConfigDrawing;
import ru.tutorialclient.ui.clickgui.objects.ModuleObject;
import ru.tutorialclient.ui.clickgui.objects.Object;
import ru.tutorialclient.ui.clickgui.theme.ThemeDrawing;
import ru.tutorialclient.util.ClientUtil;
import ru.tutorialclient.util.font.Fonts;
import ru.tutorialclient.util.render.*;
import ru.tutorialclient.util.render.animation.AnimationMath;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Panel {
    private ThemeDrawing themeDrawing = new ThemeDrawing();
    private ConfigDrawing configDrawing = new ConfigDrawing();
    Type type;
    float x,y,width,height,scrolling,scrollingOut;
    ArrayList<ModuleObject> moduleObjects = new ArrayList<>();
    public Panel(Type type, float x, float y, float width, float height){
        this.type=type;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        for(Function m: Managment.FUNCTION_MANAGER.getFunctions().stream().filter(m->m.category==type).toList()){
            moduleObjects.add(new ModuleObject(m));
        }
    }
    public void render(int mouseX,int mouseY){
        scrollingOut = AnimationMath.fast(scrollingOut, scrolling, 15);
        RenderUtil.Render2D.drawRoundedCorner(x,y,width,height,5,new Color(20,20,20,255).getRGB());
        MatrixStack ms = new MatrixStack();
        Fonts.gilroyBold[20].drawCenteredString(ms,type.name().toUpperCase(),x+width/2f,y+5,-1);
        float offset = 3;
        float off = 10;
        StencilUtil.initStencilToWrite();
        RenderUtil.Render2D.drawRoundedCorner(x+3,y+18,width-6,height-26,5,-1);
        StencilUtil.readStencilBuffer(1);

            for(ModuleObject m:moduleObjects){
                SmartScissor.push();
                SmartScissor.setFromComponentCoordinates((int) x, (int) y+20, (int) width, (int) height-22);
                m.width = width-6;
                m.height = 20;
                m.x = x+3;
                m.y = y+off+offset+scrollingOut+6;
                RenderUtil.Render2D.drawRoundedCorner(m.x,m.y,m.width,m.height,2,new Color(10,10,10).getRGB());
//            RenderUtil.Render2D.drawRect(m.x,m.y,m.width,m.height,new Color(255,255,0,100).getRGB());
                String name = m.function.name;
                if(m.isBinding)name += "...";

                if(m.function.state){
                    Fonts.msBold[17].drawString(ms, ClientUtil.gradient(name,
                                    Managment.STYLE_MANAGER.getCurrentStyle().getColor(0),
                                    Managment.STYLE_MANAGER.getCurrentStyle().getColor(90)),
                            x+8,y+off+offset+scrollingOut+16,-1);
                }else{
                    Fonts.msBold[17].drawString(ms,name,
                            x+8,y+off+offset+scrollingOut+16,-1);
                }
                float size = 10;
                float sizeY =15;
                m.expand_anim = AnimationMath.fast(m.expand_anim, m.function.expanded ? 1 : 0, 10);
                GL11.glPushMatrix();

                MatrixStack s = new MatrixStack();
                GL11.glTranslatef(-5,0,0);


                if (!m.function.settingList.isEmpty()) {

                }

                GL11.glPopMatrix();
                //end

                if(m.function.bind != 0){
                    RenderUtil.Render2D.drawShadow(x+Fonts.msBold[17].getWidth(name)+15, y+off+offset+scrollingOut+11.5F,Fonts.msBold[17].getWidth(KeyEvent.getKeyText(m.function.bind))+3,Fonts.msBold[17].getFontHeight()+1.5f,6,
                            RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(0),255),
                            RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(90),255),
                            RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(180),255),
                            RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(270),255));
                    RenderUtil.Render2D.drawRoundedCorner(x+Fonts.msBold[17].getWidth(name)+15, y+off+offset+scrollingOut+11.5F,Fonts.msBold[17].getWidth(KeyEvent.getKeyText(m.function.bind))+3,Fonts.msBold[17].getFontHeight()+1.5f,new Vector4f(4,4,4,4),
                            new Vector4i(
                                    RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(0),255),
                                    RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(90),255),
                                    RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(180),255),
                                    RenderUtil.reAlphaInt(Managment.STYLE_MANAGER.getCurrentStyle().getColor(270),255)
                            ));
                    Fonts.msBold[17].drawString(ms,KeyEvent.getKeyText(m.function.bind),
                            x+Fonts.msBold[17].getWidth(name)+16.5f,y+off+offset+scrollingOut+16,new Color(255,255,255,255).getRGB());
                }
                float yd = 5;
                for (Object object1 : m.object) {
                    object1.x = x;
                    object1.y = y+yd+off+offset+scrollingOut+25;
                    object1.width = width;
                    object1.height = 10;
                    if (object1.setting.visible()) {
                        if(m.expand_anim>0.2){
                            object1.draw(ms,mouseX,mouseY);
                        }


                        off+=(object1.height+10)*m.expand_anim;
                    }

                }
                off+=offset+20;

                SmartScissor.pop();

        }













        StencilUtil.uninitStencilBuffer();
        float max = off;
        if (max < height-6) {
            scrolling = 0;
        } else {
            scrolling = MathHelper.clamp(scrolling, -(max - (height-16)), 0);
        }
    }

    public void onClick(double mouseX, double mouseY, int button) {
        Vec2i mo = ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = mo.getX();
        mouseY = mo.getY();
        float offset = 3;
        float off = 10;

            for (ModuleObject m : moduleObjects) {

                m.mouseClicked((int) mouseX, (int) mouseY, button);
                if (RenderUtil.isInRegion(mouseX, mouseY, x + 3, y + off + offset + scrollingOut + 6, width - 6, 20) && button == 1) {
                    m.function.expanded = !m.function.expanded;
                }
                if (m.function.expanded) {
//                off+=20;
                    float yd = 5;
                    for (Object object1 : m.object) {
                        object1.y = y + yd + off + offset + scrollingOut + 25;
//                    object1.draw(ms,mouseX,mouseY);
                        if (object1.setting.visible()) {
                            off += object1.height + 5;
                        }
                    }
                }
                off += offset + 20;
            }


    }

    public void onScroll(double mouseX, double mouseY, double delta) {
        Vec2i m=ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = m.getX();
        mouseY = m.getY();

        if(RenderUtil.isInRegion(mouseX,mouseY,x,y,width,height)){
            scrolling += (float) (delta * 20);
        }
    }

    public void onRelease(double mouseX, double mouseY, int button) {
        Vec2i mo=ScaleMath.getMouse((int) mouseX, (int) mouseY);
        mouseX = mo.getX();
        mouseY = mo.getY();
        float offset = 3;
        float off = 10;

        for(ModuleObject m:moduleObjects){
            for(Object o:m.object){
                o.mouseReleased((int) mouseX, (int) mouseY,button);
            }
        }
    }

    public void onKey(int keyCode, int scanCode, int modifiers) {


        for(ModuleObject m:moduleObjects){
            m.keyTyped(keyCode, scanCode, modifiers);
        }
    }
}
