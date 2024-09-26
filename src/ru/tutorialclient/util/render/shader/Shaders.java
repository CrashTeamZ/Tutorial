package ru.tutorialclient.util.render.shader;

import ru.tutorialclient.util.render.shader.core.Shader;
import ru.tutorialclient.util.render.shader.impl.AlphaShader;

import java.util.ArrayList;
import java.util.List;

public class Shaders {

    public List<Shader> shaderList = new ArrayList<>();

    public Shaders() {

        shaderList.addAll(List.of(
           new AlphaShader("")
        ));

    }

}
