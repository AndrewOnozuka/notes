VertexBuffer = (-0.625,  0.75,   // Vertex 0: Top left ear
                -0.25,   0.25,   // Vertex 1: Left inner ear
                 0.25,   0.25,   // Vertex 2: Right inner ear
                 0.625,  0.75,   // Vertex 3: Top right ear
                -0.625, -0.625,  // Vertex 4: Bottom left
                 0.625, -0.625)  // Vertex 5: Bottom right

IndexBuffer = (0, 1, 4,  // Triangle 1: Top left ear to bottom left
               1, 2, 4,  // Triangle 2: Left inner to bottom left
               2, 5, 4,  // Triangle 3: Right inner to bottom left
               2, 3, 5)  // Triangle 4: Right ear to bottom right