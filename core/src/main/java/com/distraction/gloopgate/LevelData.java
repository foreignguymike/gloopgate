package com.distraction.gloopgate;

import com.distraction.gloopgate.entity.Slime;
import com.distraction.gloopgate.entity.Valid;

import java.util.ArrayList;
import java.util.List;

public class LevelData {

    public final Valid.Type validType;
    public final List<Slime.Type> slimeTypes;
    public final float slimeInterval;
    public final List<Slime.Type> slimeBias;
    public final int biasAmount;

    public final float speed;


    private LevelData(
        Valid.Type validType,
        List<Slime.Type> slimeTypes,
        float slimeInterval,
        List<Slime.Type> slimeBias,
        int biasAmount,
        float speed
    ) {
        this.validType = validType;
        this.slimeTypes = slimeTypes;
        this.slimeInterval = slimeInterval;
        this.slimeBias = slimeBias;
        this.biasAmount = biasAmount;
        this.speed = speed;
    }

    public static LevelData create(int level) {
        Valid.Type validType;
        List<Slime.Type> slimeTypes;
        float slimeInterval;
        List<Slime.Type> slimeBias;
        int biasAmount;
        float speed;

        if (level == 1) {
            validType = Valid.Type.VALID;
            slimeTypes = Slime.Type.random(2);
            slimeInterval = 0.7f;
            slimeBias = slimeTypes;
            biasAmount = 3;
            speed = 14;
        } else if (level == 2) {
            validType = Valid.Type.VALID;
            slimeTypes = Slime.Type.random(3);
            slimeInterval = 0.6f;
            slimeBias = slimeTypes;
            biasAmount = 4;
            speed = 16;
        } else if (level == 3) {
            validType = Valid.Type.INVALID;
            slimeTypes = Slime.Type.random(3);
            slimeInterval = 0.6f;
            slimeBias = new ArrayList<>();
            biasAmount = 1;
            speed = 16;
        } else if (level == 4) {
            validType = Valid.Type.VALID;
            slimeTypes = Slime.Type.random(4);
            slimeInterval = 0.5f;
            slimeBias = slimeTypes;
            biasAmount = 2;
            speed = 18;
        } else {
            validType = Valid.Type.INVALID;
            slimeTypes = Slime.Type.random(4);
            slimeInterval = 0.4f;
            slimeBias = new ArrayList<>();
            biasAmount = 1;
            speed = 20;
        }

        return new LevelData(validType, slimeTypes, slimeInterval, slimeBias, biasAmount, speed);
    }
}
