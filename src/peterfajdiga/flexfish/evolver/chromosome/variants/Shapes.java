package peterfajdiga.flexfish.evolver.chromosome.variants;

import peterfajdiga.flexfish.evolver.chromosome.ShapeFormat;
import peterfajdiga.flexfish.evolver.chromosome.expressor.ManyManyExpressor;
import peterfajdiga.flexfish.evolver.chromosome.expressor.OneManyExpressor;
import peterfajdiga.flexfish.evolver.chromosome.trait.IntTrait;
import peterfajdiga.flexfish.evolver.chromosome.trait.Trait;

import java.util.ArrayList;
import java.util.List;

class Shapes {
    private Shapes() {}

    static ShapeFormat geneticShape(final int length) {
        return new ShapeFormat(
                length,
                new OneManyExpressor<>(new IntTrait(3, 6), length),
                new OneManyExpressor<>(new IntTrait(2, 6), length),
                "full"
        );
    }

    static ShapeFormat boxShape(final int length, final int height, final int width) {
        final Trait[] heightTraits = new Trait[length];
        final Trait[] widthTraits  = new Trait[length];
        for (int i = 0; i < length; i++) {
            heightTraits[i] = new IntTrait(height);
            widthTraits[i] = new IntTrait(width);
        }
        return new ShapeFormat(
                length,
                new ManyManyExpressor<>(heightTraits),
                new ManyManyExpressor<>(widthTraits),
                width == 2 ? "tape" : width == 6 ? "worm" : "box"
        );
    }

    static ShapeFormat fishShape() {
        final List<Trait> heightTraits = new ArrayList<>();
        final List<Trait> widthTraits  = new ArrayList<>();
        heightTraits.add(new IntTrait(6)); widthTraits.add(new IntTrait(2));
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(2));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(2));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(4));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(4));
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(4));
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(5));
        heightTraits.add(new IntTrait(6)); widthTraits.add(new IntTrait(5));
        heightTraits.add(new IntTrait(6)); widthTraits.add(new IntTrait(5));
        heightTraits.add(new IntTrait(6)); widthTraits.add(new IntTrait(5));
        heightTraits.add(new IntTrait(6)); widthTraits.add(new IntTrait(5));
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(5));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(5));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(4));
        heightTraits.add(new IntTrait(2)); widthTraits.add(new IntTrait(2));
        assert heightTraits.size() == widthTraits.size();
        return new ShapeFormat(
                heightTraits.size(),
                new ManyManyExpressor<>(heightTraits.toArray(new Trait[0])),
                new ManyManyExpressor<>(widthTraits .toArray(new Trait[0])),
                "fish"
        );
    }

    static ShapeFormat tuShape() {
        final List<Trait> heightTraits = new ArrayList<>();
        final List<Trait> widthTraits  = new ArrayList<>();
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(1));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(1));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(1));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(1));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(1));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(2));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(2));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(2));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(6)); widthTraits.add(new IntTrait(4));
        heightTraits.add(new IntTrait(6)); widthTraits.add(new IntTrait(4));
        heightTraits.add(new IntTrait(6)); widthTraits.add(new IntTrait(4));
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(5)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(4)); widthTraits.add(new IntTrait(3));
        heightTraits.add(new IntTrait(3)); widthTraits.add(new IntTrait(2));
        heightTraits.add(new IntTrait(2)); widthTraits.add(new IntTrait(2));
        heightTraits.add(new IntTrait(1)); widthTraits.add(new IntTrait(1));
        assert heightTraits.size() == widthTraits.size();
        return new ShapeFormat(
                heightTraits.size(),
                new ManyManyExpressor<>(heightTraits.toArray(new Trait[0])),
                new ManyManyExpressor<>(widthTraits .toArray(new Trait[0])),
                "tan"
        );
    }
}
