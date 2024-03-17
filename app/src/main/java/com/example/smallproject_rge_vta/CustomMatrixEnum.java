package com.example.smallproject_rge_vta;

public enum CustomMatrixEnum {

    DESATURATE(new float[]{
            0.33f, 0.59f, 0.11f, 0, 0,  // Rouge
            0.33f, 0.59f, 0.11f, 0, 0,  // Vert
            0.33f, 0.59f, 0.11f, 0, 0,  // Bleu
            0, 0, 0, 1, 0   // Alpha
    }),
    SATURATE(new float[] {
            1.25f, 0, 0, 0, 0, // Rouge
            0, 1.25f, 0, 0, 0, // Vert
            0, 0, 1.25f, 0, 0, // Bleu
            0, 0,  0  , 1, 0     // Alpha
    }),
    NEGATIVE(new float[] {
            -1.0f, 0, 0, 0, 255, // Rouge
            0, -1.0f, 0, 0, 255, // Vert
            0, 0, -1.0f, 0, 255, // Bleu
            0, 0, 0, 1, 0     // Alpha
    }),
    POSITIVE(new float[] {
            1.0f, 0, 0, 0, 0, // Rouge
            0, 1.0f, 0, 0, 0, // Vert
            0, 0, 1.0f, 0, 0, // Bleu
            0, 0, 0, 1, 0     // Alpha
    }),
    SEPIA(new float[] {
            0.393f, 0.769f, 0.189f, 0, 0,  // Rouge
            0.349f, 0.686f, 0.168f, 0, 0,  // Vert
            0.272f, 0.534f, 0.131f, 0, 0,  // Bleu
            0,      0,      0,      1, 0   // Alpha
    }),
    RED(new float[] {
            1, 0, 0, 0, 0,  // Rouge
            0, 0, 0, 0, 0,  // Vert
            0, 0, 0, 0, 0,  // Bleu
            0, 0, 0, 1, 0   // Alpha
    }),
    GREEN(new float[] {
            0, 0, 0, 0, 0,  // Rouge
            0, 1, 0, 0, 0,  // Vert
            0, 0, 0, 0, 0,  // Bleu
            0, 0, 0, 1, 0   // Alpha
    }),
    BLUE(new float[] {
            0, 0, 0, 0, 0,  // Rouge
            0, 0, 0, 0, 0,  // Vert
            0, 0, 1, 0, 0,  // Bleu
            0, 0, 0, 1, 0   // Alpha
    });

    private final float[] floatMatrix;

    CustomMatrixEnum(float[] floatMatrix) {
        this.floatMatrix = floatMatrix;
    }

    public float[] getFloatMatrix() {
        return floatMatrix;
    }
}
