#version 110

uniform sampler2D DiffuseSampler;
uniform sampler2D OverlaySampler;
uniform sampler2D EmissiveOverlaySampler;
uniform float Opacity;

varying vec2 texCoord;

void main(){
    gl_FragColor = texture2D(DiffuseSampler, texCoord);
    vec4 overlay = texture2D(OverlaySampler, vec2(texCoord.x, (1.0 - texCoord.y)));
    vec4 emissiveOverlay = texture2D(EmissiveOverlaySampler, vec2(texCoord.x, (1.0 - texCoord.y)));
    float emmissivity = Opacity * 2.666666667;
    gl_FragColor.rgb = mix(mix(gl_FragColor.rgb,overlay.rgb,overlay.a*Opacity).rgb,emissiveOverlay.rgb,emissiveOverlay.a*emmissivity).rgb;
}
