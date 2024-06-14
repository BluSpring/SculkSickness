#version 110

uniform sampler2D DiffuseSampler;
uniform sampler2D VignetteOverlaySampler;

varying vec2 texCoord;

void main(){
    gl_FragColor = texture2D(DiffuseSampler, texCoord);
    vec4 vignetteOverlay = texture2D(VignetteOverlaySampler, vec2(texCoord.x, (1.0 - texCoord.y)));
    gl_FragColor.rgb = mix(gl_FragColor.rgb,vignetteOverlay.rgb, vignetteOverlay.a).rgb;
}
