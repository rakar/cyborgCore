package org.montclairrobotics.cyborg.core.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class CBNetworkTable implements CBSource {

    private String sourceEntry;
    public NetworkTable table;

    public CBNetworkTable(String path) {
        table = NetworkTableInstance.getDefault().getTable(path);
    }

    public CBNetworkTable setSourceEntry(String entry) {
        sourceEntry = entry;
        return this;
    }

    @Override
    public double get() {
        return table.getEntry(sourceEntry).getDouble(0);
    }

    public CBEntrySource getEntrySource(String sourceEntry) {
        return new CBEntrySource(sourceEntry);
    }

    public class CBEntrySource implements CBSource  {
        String sourceEntry;

        public CBEntrySource(String entry) {
            sourceEntry = entry;
        }

        @Override
        public double get() {
            return table.getEntry(sourceEntry).getDouble(0);
        }
    }

}
