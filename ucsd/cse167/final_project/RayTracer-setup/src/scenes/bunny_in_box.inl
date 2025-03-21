#define GLM_FORCE_RADIANS
#define GLM_ENABLE_EXPERIMENTAL

#include <glm/glm.hpp>
#include <glm/gtx/transform.hpp>

#include "GlossyMaterial.h"
#include "Obj.h"
#include "RayTracer.h"
#include "Sphere.h"
#include "Square.h"

using namespace glm;

Scene* bunny_in_box() {
    // Materials
    std::shared_ptr<GlossyMaterial> diffuse_material_white_wall = std::make_shared<GlossyMaterial>(vec3(0.99f), vec3(0.0f), 0.0f);
    std::shared_ptr<GlossyMaterial> diffuse_material_red_wall = std::make_shared<GlossyMaterial>(vec3(1.0f, 0.0f, 0.0f), vec3(0.0f), 0.0f);
    std::shared_ptr<GlossyMaterial> diffuse_material_green_wall = std::make_shared<GlossyMaterial>(vec3(0.0f, 1.0f, 0.0f), vec3(0.0f), 0.0f);
    
    // Fully reflective mirror-like material for the bunny
    std::shared_ptr<GlossyMaterial> glossy_material = std::make_shared<GlossyMaterial>(
        vec3(0.0f),  // C_diffuse
        vec3(0.95f), // C_specular (very reflective)
        1.0f         // shininess = 1 → always mirror
    );

    std::shared_ptr<GlossyMaterial> light_material = std::make_shared<GlossyMaterial>(vec3(0.0f), vec3(0.0f), 0.5f);
    light_material->convert_to_light(vec3(1.0f), vec3(70.0f));  // color, intensity

    std::unique_ptr<Node> root_node = std::make_unique<Node>();

    std::unique_ptr<Node> bottom_square = std::make_unique<Node>();
    bottom_square->model = std::make_unique<Square>(vec3(0.0f), 4.0f, vec3(0.0f, 1.0f, 0.0f), diffuse_material_white_wall);
    root_node->childnodes.push_back(std::move(bottom_square));
    root_node->childtransforms.push_back(translate(vec3(0.0f, -2.0f, 0.0f)));

    std::unique_ptr<Node> right_square = std::make_unique<Node>();
    right_square->model = std::make_unique<Square>(vec3(0.0f), 4.0f, vec3(-1.0f, 0.0f, 0.0f), diffuse_material_green_wall);
    root_node->childnodes.push_back(std::move(right_square));
    root_node->childtransforms.push_back(translate(vec3(2.0f, 0.0f, 0.0f)));

    std::unique_ptr<Node> left_square = std::make_unique<Node>();
    left_square->model = std::make_unique<Square>(vec3(0.0f), 4.0f, vec3(1.0f, 0.0f, 0.0f), diffuse_material_red_wall);
    root_node->childnodes.push_back(std::move(left_square));
    root_node->childtransforms.push_back(translate(vec3(-2.0f, 0.0f, 0.0f)));

    std::unique_ptr<Node> up_square = std::make_unique<Node>();
    up_square->model = std::make_unique<Square>(vec3(0.0f), 4.0f, vec3(0.0f, -1.0f, 0.0f), diffuse_material_white_wall);
    root_node->childnodes.push_back(std::move(up_square));
    root_node->childtransforms.push_back(translate(vec3(0.0f, 2.0f, 0.0f)));

    std::unique_ptr<Node> back_square = std::make_unique<Node>();
    back_square->model = std::make_unique<Square>(vec3(0.0f), 4.0f, vec3(0.0f, 0.0f, 1.0f), diffuse_material_white_wall);
    root_node->childnodes.push_back(std::move(back_square));
    root_node->childtransforms.push_back(translate(vec3(0.0f, 0.0f, -2.0f)));

    std::unique_ptr<Node> square_light = std::make_unique<Node>();
    square_light->model = std::make_unique<Square>(vec3(0.0f), 1.0f, vec3(0.0f, -1.0f, 0.0f), light_material);
    root_node->childnodes.push_back(std::move(square_light));
    root_node->childtransforms.push_back(translate(vec3(0.0f, 1.95f, 0.0f)));

    std::unique_ptr<Node> bunny = std::make_unique<Node>();
    bunny->model = std::make_unique<Obj>("../models/bunny.obj", glossy_material);
    root_node->childnodes.push_back(std::move(bunny));
    root_node->childtransforms.push_back(
        translate(vec3(0.0f, -1.0f, 0.0f)) *
        scale(vec3(1.0f)) *
        rotate(degree_to_rad(30.0f), vec3(0.0f, 1.0f, 0.0f))
    );

    return new Scene(std::move(root_node));
}