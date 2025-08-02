package com.distraction.gloopgate;

import com.distraction.gloopgate.entity.Slime;
import com.distraction.gloopgate.entity.Valid;

import java.util.ArrayList;
import java.util.List;

public class LevelData {

    public enum Difficulty {
        NORMAL("Normal"),
        HARD("Hard"),
        WEIRD("Weird")
        ;

        public final String text;
        Difficulty(String text) {
            this.text = text;
        }

        public static Difficulty from(int value) {
            if (value == 0) return NORMAL;
            else if (value == 1) return HARD;
            else return WEIRD;
        }
    }

    public final Valid.Type validType;
    public final List<Slime.Type> slimeTypes;
    public final float slimeInterval;
    public final List<Slime.Type> slimeBias;
    public final int biasAmount;

    public final float speed;
    public final int slimeCount;

    private LevelData(
        Valid.Type validType,
        List<Slime.Type> slimeTypes,
        float slimeInterval,
        List<Slime.Type> slimeBias,
        int biasAmount,
        float speed, int slimeCount
    ) {
        this.validType = validType;
        this.slimeTypes = slimeTypes;
        this.slimeInterval = slimeInterval;
        this.slimeBias = slimeBias;
        this.biasAmount = biasAmount;
        this.speed = speed;
        this.slimeCount = slimeCount;
    }

    public static LevelData create(Difficulty difficulty, int level) {
        Valid.Type validType;
        List<Slime.Type> slimeTypes;
        float slimeInterval;
        List<Slime.Type> slimeBias;
        int biasAmount;
        float speed;
        int slimeCount;

        if (difficulty == Difficulty.NORMAL) {
            if (level == 1) {
                validType = Valid.Type.VALID;
                slimeTypes = Slime.Type.random(2);
                slimeInterval = 0.6f;
                slimeBias = slimeTypes;
                biasAmount = 3;
                speed = 14;
                slimeCount = 20;
            } else if (level == 2) {
                validType = Valid.Type.VALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.6f;
                slimeBias = slimeTypes;
                biasAmount = 2;
                speed = 16;
                slimeCount = 25;
            } else if (level == 3) {
                validType = Valid.Type.INVALID;
                slimeTypes = Slime.Type.random(2);
                slimeInterval = 0.6f;
                slimeBias = new ArrayList<>();
                biasAmount = 1;
                speed = 16;
                slimeCount = 25;
            } else if (level == 4) {
                validType = Valid.Type.VALID;
                slimeTypes = Slime.Type.random(4);
                slimeInterval = 0.5f;
                slimeBias = slimeTypes;
                biasAmount = 2;
                speed = 18;
                slimeCount = 30;
            } else {
                validType = Valid.Type.INVALID;
                slimeTypes = Slime.Type.random(2);
                slimeInterval = 0.4f;
                slimeBias = new ArrayList<>();
                biasAmount = 1;
                speed = 20;
                slimeCount = 40;
            }
        } else if (difficulty == Difficulty.HARD) {
            if (level == 1) {
                validType = Valid.Type.VALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.5f;
                slimeBias = slimeTypes;
                biasAmount = 3;
                speed = 20;
                slimeCount = 20;
            } else if (level == 2) {
                validType = Valid.Type.VALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.5f;
                slimeBias = slimeTypes;
                biasAmount = 2;
                speed = 21;
                slimeCount = 25;
            } else if (level == 3) {
                validType = Valid.Type.INVALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.4f;
                slimeBias = new ArrayList<>();
                biasAmount = 1;
                speed = 22;
                slimeCount = 30;
            } else if (level == 4) {
                validType = Valid.Type.VALID;
                slimeTypes = Slime.Type.random(4);
                slimeInterval = 0.4f;
                slimeBias = slimeTypes;
                biasAmount = 2;
                speed = 23;
                slimeCount = 40;
            } else {
                validType = Valid.Type.INVALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.4f;
                slimeBias = new ArrayList<>();
                biasAmount = 1;
                speed = 25;
                slimeCount = 40;
            }
        } else {
            if (level == 1) {
                validType = Valid.Type.INVALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.4f;
                slimeBias = slimeTypes;
                biasAmount = 1;
                speed = 20;
                slimeCount = 30;
            } else if (level == 2) {
                validType = Valid.Type.INVALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.4f;
                slimeBias = slimeTypes;
                biasAmount = 1;
                speed = 20;
                slimeCount = 35;
            } else if (level == 3) {
                validType = Valid.Type.INVALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.4f;
                slimeBias = new ArrayList<>();
                biasAmount = 1;
                speed = 20;
                slimeCount = 40;
            } else if (level == 4) {
                validType = Valid.Type.INVALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.3f;
                slimeBias = new ArrayList<>();
                biasAmount = 1;
                speed = 25;
                slimeCount = 50;
            } else {
                validType = Valid.Type.INVALID;
                slimeTypes = Slime.Type.random(3);
                slimeInterval = 0.2f;
                slimeBias = new ArrayList<>();
                biasAmount = 1;
                speed = 30;
                slimeCount = 60;
            }
        }

        return new LevelData(validType, slimeTypes, slimeInterval, slimeBias, biasAmount, speed, slimeCount);
    }
}
