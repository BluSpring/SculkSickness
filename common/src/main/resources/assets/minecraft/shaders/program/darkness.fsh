#version 130

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;

uniform mat4 ProjMat;
uniform vec2 InSize;
uniform vec2 OutSize;
uniform vec2 ScreenSize;
uniform float _FOV;

varying vec2 texCoord;

float near = 0.1;
float far = 1000.0;
float LinearizeDepth(float depth) {
    float z = depth * 2.0 - 1.0;
    return (near * far) / (far + near - z * (far - near));
}

void main(){
    float depth = LinearizeDepth(texture2D(DiffuseDepthSampler, texCoord).r);
    vec4 color = texture2D(DiffuseSampler,texCoord);
    gl_FragColor = vec4(color.rgb/depth,color.a);
}
