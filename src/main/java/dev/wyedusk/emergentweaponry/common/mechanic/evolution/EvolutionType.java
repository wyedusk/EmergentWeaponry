package dev.wyedusk.emergentweaponry.common.mechanic.evolution;

/**
 * Defines all item evolution types.
 */
public enum EvolutionType {
    /**
     * The IMPROVEMENT evolution type solely acts as an improvement to an item's statistics, for example
     * swords can become stronger and deal more damage, pickaxes become capable of mining faster, armour
     * can absorb more damage, and so on. Internally, an IMPROVEMENT evolution increases an item's
     * improvementTier value in its evolution data.
     */
    IMPROVEMENT,
    /**
     * The TRANSFORM evolution type changes the item's type, for example evolving an iron sword into
     * a diamond sword or into a custom item type. A transformation evolution clears all IMPROVEMENT
     * evolutions applied to an item. Unlike IMPROVEMENT, this evolution type is not always available
     * on an item.
     */
    TRANSFORM
}
