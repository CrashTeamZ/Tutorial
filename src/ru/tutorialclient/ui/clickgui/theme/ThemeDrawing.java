package ru.tutorialclient.ui.clickgui.theme;

import com.mojang.blaze3d.matrix.MatrixStack;
import org.joml.Vector4i;
import ru.tutorialclient.managment.Managment;
import ru.tutorialclient.ui.midnight.Style;
import ru.tutorialclient.util.render.ColorUtil;
import ru.tutorialclient.util.render.RenderUtil;
import ru.tutorialclient.util.render.animation.AnimationMath;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ru.tutorialclient.ui.clickgui.Window.scrollingOut;

public class ThemeDrawing {

    public List<ThemeObject> objects = new ArrayList<>();

    float animation;

    public ThemeDrawing() {
        Style custom = Managment.STYLE_MANAGER.styles.get(Managment.STYLE_MANAGER.styles.size() - 1);
        for (Style style : Managment.STYLE_MANAGER.styles) {
            if (style.name.equalsIgnoreCase("Свой цвет")) continue;
            objects.add(new ThemeObject(style));
        }
        float[] rgb = RenderUtil.IntColor.rgb(custom.colors[edit]);
        float[] hsb = Color.RGBtoHSB((int) (rgb[0] * 255), (int) (rgb[1] * 255), (int) (rgb[2] * 255), null);
        this.hsb = hsb[0];
        this.satur = hsb[1];
        this.brithe = hsb[2];
        themeDrawing = this;  // Initialize it here if needed

    }

    boolean colorOpen;
    public float openAnimation;

    public int edit;

    float x, y, width, height;

    float hsb;
    float satur;
    float brithe;
    private final ThemeDrawing themeDrawing;

    public void draw(MatrixStack stack, int mouseX, int mouseY, float x,float y,float width ,float height) {
        this.x =  x;
        this.y = y;
        this.width = width;
        this.height = height;


        openAnimation = AnimationMath.lerp(openAnimation, colorOpen ? 1 : 0, 15);

        float rowLimit = 5; // Количество тем в ряду
        float offset = 3;
        float off = 10;

        for (int i = 0; i < themeDrawing.objects.size(); i++) {

            ThemeObject object = themeDrawing.objects.get(i);
            RenderUtil.Render2D.drawRoundedCorner(x + 65 + (i % rowLimit) * (object.width + offset)  , y + off + offset  + 285, 60, 55, 3, ColorUtil.rgba(25, 26, 33, 255));
            object.width = 50;
            object.height = 20;

            // Вычисляем координаты для текущего элемента
            object.x = x + 70 + (i % rowLimit) * (object.width + offset);
            object.y = y + off + offset * (i / rowLimit) + scrollingOut + 290;

            object.draw(stack, mouseX, mouseY);

            RenderUtil.Render2D.drawRoundOutline(object.x, object.y, object.width, object.height, 5f, 0f,
                    ColorUtil.rgba(25, 26, 33, 255), new Vector4i(
                            ColorUtil.rgba(25, 26, 33, 0), ColorUtil.rgba(25, 26, 33, 0),
                            ColorUtil.rgba(25, 26, 33, 0), ColorUtil.rgba(25, 26, 33, 0)
                    ));

            if (i % rowLimit == rowLimit - 1) {
                // Переход к следующему ряду
                off += offset + 20;
            }
        }

        for (ThemeObject object : objects) {
            object.draw(stack,mouseX,mouseY);
        }
        Style custom = Managment.STYLE_MANAGER.styles.get(Managment.STYLE_MANAGER.styles.size() - 1);

        animation = (float) AnimationMath.lerp(animation, Managment.STYLE_MANAGER.getCurrentStyle() == custom ? 1 : RenderUtil.isInRegion(mouseX,mouseY,x + 10, y + height - 65, width - 20, 50) ? 0.5f : 0, 5);






    }

    boolean drag;

    public void click(int mouseX, int mouseY, int button) {
        Style custom = Managment.STYLE_MANAGER.styles.get(Managment.STYLE_MANAGER.styles.size() - 1);


        float colorX = x + width + 20;
        float colorY = y + height - 315 / 2f;



        if (RenderUtil.isInRegion(mouseX, mouseY, x + 10, y + height - 65, width - 20, 50) && button == 0) {
            Style c = Managment.STYLE_MANAGER.styles.get(Managment.STYLE_MANAGER.styles.size() - 1);
            Managment.STYLE_MANAGER.setCurrentStyle(c);
        }
        for (ThemeObject object : objects) {
            if (RenderUtil.isInRegion(mouseX, mouseY, object.x, object.y, object.width, object.height)) {
                Managment.STYLE_MANAGER.setCurrentStyle(object.style);
            }
        }
    }
}