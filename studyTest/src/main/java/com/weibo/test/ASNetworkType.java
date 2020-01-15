package com.weibo.test;

public enum ASNetworkType {
        WIFI("wifi", "NETWORK_TYPE_WIFI"), G2("2g", "NETWORK_TYPE_MOBILE"), G3("3g", "NETWORK_TYPE_EDGE"), G4("4g", "NETWORK_TYPE_LTE"), CELL(
                "cell", "NETWORK_TYPE_LTE"), UNKNOWN("unknown", "NETWORK_TYPE_UNKNOWN");

        private String originalType;
        private String type;

        ASNetworkType(String originalType, String type) {
            this.originalType = originalType;
            this.type = type;
        }

        public static ASNetworkType parseNetworkType(String originalType) {
            if (originalType == null) {
                return UNKNOWN;
            }
            for (ASNetworkType asNetworkType : ASNetworkType.values()) {
                if (originalType.equals(asNetworkType.getOriginalType())) {
                    return asNetworkType;
                }
            }
            return UNKNOWN;
        }

        public String getOriginalType() {
            return originalType;
        }

        public String getType() {
            return type;
        }
    }