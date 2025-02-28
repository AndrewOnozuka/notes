#version 330 core

in vec4 position; // raw position in the model coord
in vec3 normal;   // raw normal in the model coord

uniform mat4 modelview; // from model coord to eye coord
uniform mat4 view;      // from world coord to eye coord

// Material parameters
uniform vec4 ambient;
uniform vec4 diffuse;
uniform vec4 specular;
uniform vec4 emision;
uniform float shininess;

// Light source parameters
const int maximal_allowed_lights = 10;
uniform bool enablelighting;
uniform int nlights;
uniform vec4 lightpositions[ maximal_allowed_lights ];
uniform vec4 lightcolors[ maximal_allowed_lights ];

// Output the frag color
out vec4 fragColor;


void main (void){
    if (!enablelighting){
        // Default normal coloring (you don't need to modify anything here)
        vec3 N = normalize(normal);
        fragColor = vec4(0.5f*N + 0.5f , 1.0f);
    } else {
        
        // HW3: You will compute the lighting here.
        // Transform normal to eye space
        mat3 normalMatrix = transpose(inverse(mat3(modelview))); // Correct normal transformation
        vec3 N = normalize(normalMatrix * normal);

        // Compute fragment position in eye space
        vec4 fragPos4D = modelview * position; // 4D position
        vec3 fragPos = fragPos4D.xyz / fragPos4D.w; // Dehomogenization

        // Initialize color with self-emission
        vec3 resultColor = emision.rgb;

        for (int i = 0; i < nlights; i++) {
            // Transform light position to eye space
            vec4 lightPos4D = view * lightpositions[i];

            // Compute light direction **correctly handling w=0 case**
            vec3 L = normalize(
                fragPos4D.w * lightPos4D.xyz - lightPos4D.w * fragPos4D.xyz
            );

            // Compute view direction (camera is at (0,0,0) in eye space)
            vec3 V = normalize(-fragPos);

            // Compute halfway vector
            vec3 H = normalize(V + L);

            // Compute ambient component
            vec3 ambientComponent = ambient.rgb * lightcolors[i].rgb;

            // Compute diffuse component
            float diff = max(dot(N, L), 0.0);
            vec3 diffuseComponent = diffuse.rgb * diff * lightcolors[i].rgb;

            // Compute specular component (Blinn-Phong)
            float spec = pow(max(dot(N, H), 0.0), shininess);
            vec3 specularComponent = specular.rgb * spec * lightcolors[i].rgb;

            // Sum all components
            resultColor += ambientComponent + diffuseComponent + specularComponent;
        }

        // Final output color
        fragColor = vec4(resultColor, 1.0);
    }
}
