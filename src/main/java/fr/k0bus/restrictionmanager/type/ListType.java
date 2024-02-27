package fr.k0bus.restrictionmanager.type;

public enum ListType {
    BLACKLIST(true),
    WHITELIST(false);

    private final boolean blacklistMode;

    ListType(boolean value)
    {
        this.blacklistMode = value;
    }

    public static ListType fromString(String s)
    {
        for(ListType type:ListType.values())
        {
            if(type.name().equalsIgnoreCase(s)) return type;
        }
        return BLACKLIST;
    }

    public boolean isBlacklistMode() {
        return blacklistMode;
    }
}
