package ic20b106.client.game.entities;

import ic20b106.client.Game;
import ic20b106.client.game.buildings.Material;
import ic20b106.client.game.buildings.Soldier;
import ic20b106.client.game.buildings.link.LinkDirection;
import ic20b106.client.game.buildings.storage.Storable;
import ic20b106.shared.PlayerColor;
import javafx.scene.image.Image;

public class CarrierSprites {

    static class RED {
        static class LEFT {
            private static final String PATH = "/images/red/entities/left/";
            public static final Image EMTPY =          createImage(PATH + "empty.png");
            public static final Image WOOD =           createImage(PATH + "wood.png");
            public static final Image ROCK =           createImage(PATH + "rock.png");
            public static final Image METAL =          createImage(PATH + "metal.png");
            public static final Image PRIVATE =        createImage(PATH + "private.png");

            /**
             * Gets correct Carrier Sprite
             *
             * @param storable Item the Carrier is holding
             * @return Carrier Sprite
             */
            public static Image getCarrierSprite(Storable storable) {
                return CarrierSprites.getImage(storable, WOOD, ROCK, METAL, PRIVATE, EMTPY);
            }
        }

        static class RIGHT {
            private static final String PATH = "/images/red/entities/right/";
            public static final Image EMTPY =         createImage(PATH + "empty.png");
            public static final Image WOOD =          createImage(PATH + "wood.png");
            public static final Image ROCK =          createImage(PATH + "rock.png");
            public static final Image METAL =         createImage(PATH + "metal.png");
            public static final Image PRIVATE =       createImage(PATH + "private.png");

            /**
             * Gets correct Carrier Sprite
             *
             * @param storable Item the Carrier is holding
             * @return Carrier Sprite
             */
            public static Image getCarrierSprite(Storable storable) {
                return CarrierSprites.getImage(storable, WOOD, ROCK, METAL, PRIVATE, EMTPY);
            }
        }

        /**
         * Gets correct Carrier Sprite
         *
         * @param direction Direction of the Carrier
         * @param storable Item the Carrier is holding
         * @return Carrier Sprite
         */
        public static Image getCarrierSprite(LinkDirection direction, Storable storable) {
            return switch (direction) {
                case TOP_LEFT, LEFT, BOTTOM_LEFT -> LEFT.getCarrierSprite(storable);
                case TOP_RIGHT, RIGHT, BOTTOM_RIGHT -> RIGHT.getCarrierSprite(storable);
            };
        }
    }

    static class GREEN {
        static class LEFT {
            private static final String PATH = "/images/green/entities/left/";
            public static final Image EMTPY =          createImage(PATH + "empty.png");
            public static final Image WOOD =           createImage(PATH + "wood.png");
            public static final Image ROCK =           createImage(PATH + "rock.png");
            public static final Image METAL =          createImage(PATH + "metal.png");
            public static final Image PRIVATE =        createImage(PATH + "private.png");

            /**
             * Gets correct Carrier Sprite
             *
             * @param storable Item the Carrier is holding
             * @return Carrier Sprite
             */
            public static Image getCarrierSprite(Storable storable) {
                return CarrierSprites.getImage(storable, WOOD, ROCK, METAL, PRIVATE, EMTPY);
            }
        }

        static class RIGHT {
            private static final String PATH = "/images/green/entities/right/";
            public static final Image EMTPY =         createImage(PATH + "empty.png");
            public static final Image WOOD =          createImage(PATH + "wood.png");
            public static final Image ROCK =          createImage(PATH + "rock.png");
            public static final Image METAL =         createImage(PATH + "metal.png");
            public static final Image PRIVATE =       createImage(PATH + "private.png");

            /**
             * Gets correct Carrier Sprite
             *
             * @param storable Item the Carrier is holding
             * @return Carrier Sprite
             */
            public static Image getCarrierSprite(Storable storable) {
                return CarrierSprites.getImage(storable, WOOD, ROCK, METAL, PRIVATE, EMTPY);
            }
        }

        /**
         * Gets correct Carrier Sprite
         *
         * @param direction Direction of the Carrier
         * @param storable Item the Carrier is holding
         * @return Carrier Sprite
         */
        public static Image getCarrierSprite(LinkDirection direction, Storable storable) {
            return switch (direction) {
                case TOP_LEFT, LEFT, BOTTOM_LEFT -> LEFT.getCarrierSprite(storable);
                case TOP_RIGHT, RIGHT, BOTTOM_RIGHT -> RIGHT.getCarrierSprite(storable);
            };
        }
    }

    static class BLUE {
        static class LEFT {
            private static final String PATH = "/images/blue/entities/left/";
            public static final Image EMTPY =          createImage(PATH + "empty.png");
            public static final Image WOOD =           createImage(PATH + "wood.png");
            public static final Image ROCK =           createImage(PATH + "rock.png");
            public static final Image METAL =          createImage(PATH + "metal.png");
            public static final Image PRIVATE =        createImage(PATH + "private.png");

            /**
             * Gets correct Carrier Sprite
             *
             * @param storable Item the Carrier is holding
             * @return Carrier Sprite
             */
            public static Image getCarrierSprite(Storable storable) {
                return CarrierSprites.getImage(storable, WOOD, ROCK, METAL, PRIVATE, EMTPY);
            }
        }

        static class RIGHT {
            private static final String PATH = "/images/blue/entities/right/";
            public static final Image EMTPY =         createImage(PATH + "empty.png");
            public static final Image WOOD =          createImage(PATH + "wood.png");
            public static final Image ROCK =          createImage(PATH + "rock.png");
            public static final Image METAL =         createImage(PATH + "metal.png");
            public static final Image PRIVATE =       createImage(PATH + "private.png");

            /**
             * Gets correct Carrier Sprite
             *
             * @param storable Item the Carrier is holding
             * @return Carrier Sprite
             */
            public static Image getCarrierSprite(Storable storable) {
                return CarrierSprites.getImage(storable, WOOD, ROCK, METAL, PRIVATE, EMTPY);
            }
        }

        /**
         * Gets correct Carrier Sprite
         *
         * @param direction Direction of the Carrier
         * @param storable Item the Carrier is holding
         * @return Carrier Sprite
         */
        public static Image getCarrierSprite(LinkDirection direction, Storable storable) {
            return switch (direction) {
                case TOP_LEFT, LEFT, BOTTOM_LEFT -> LEFT.getCarrierSprite(storable);
                case TOP_RIGHT, RIGHT, BOTTOM_RIGHT -> RIGHT.getCarrierSprite(storable);
            };
        }
    }

    static class YELLOW {
        static class LEFT {
            private static final String PATH = "/images/yellow/entities/left/";
            public static final Image EMTPY =          createImage(PATH + "empty.png");
            public static final Image WOOD =           createImage(PATH + "wood.png");
            public static final Image ROCK =           createImage(PATH + "rock.png");
            public static final Image METAL =          createImage(PATH + "metal.png");
            public static final Image PRIVATE =        createImage(PATH + "private.png");

            /**
             * Gets correct Carrier Sprite
             *
             * @param storable Item the Carrier is holding
             * @return Carrier Sprite
             */
            public static Image getCarrierSprite(Storable storable) {
                return CarrierSprites.getImage(storable, WOOD, ROCK, METAL, PRIVATE, EMTPY);
            }
        }

        static class RIGHT {
            private static final String PATH = "/images/yellow/entities/right/";
            public static final Image EMTPY =         createImage(PATH + "empty.png");
            public static final Image WOOD =          createImage(PATH + "wood.png");
            public static final Image ROCK =          createImage(PATH + "rock.png");
            public static final Image METAL =         createImage(PATH + "metal.png");
            public static final Image PRIVATE =       createImage(PATH + "private.png");

            /**
             * Gets correct Carrier Sprite
             *
             * @param storable Item the Carrier is holding
             * @return Carrier Sprite
             */
            public static Image getCarrierSprite(Storable storable) {
                return CarrierSprites.getImage(storable, WOOD, ROCK, METAL, PRIVATE, EMTPY);
            }
        }

        /**
         * Gets correct Carrier Sprite
         *
         * @param direction Direction of the Carrier
         * @param storable Item the Carrier is holding
         * @return Carrier Sprite
         */
        public static Image getCarrierSprite(LinkDirection direction, Storable storable) {
            return switch (direction) {
                case TOP_LEFT, LEFT, BOTTOM_LEFT -> LEFT.getCarrierSprite(storable);
                case TOP_RIGHT, RIGHT, BOTTOM_RIGHT -> RIGHT.getCarrierSprite(storable);
            };
        }
    }

    /**
     * Gets correct Carrier Sprite
     *
     * @param storable Item the Carrier is holding
     * @param wood Carrier Wood Image
     * @param rock Carrier Rock Image
     * @param metal Carrier Metal Image
     * @param aPrivate Carrier Private Image
     * @param emtpy Carrier Empty Image
     * @return Carrier Sprite
     */
    private static Image getImage(Storable storable, Image wood, Image rock, Image metal, Image aPrivate, Image emtpy) {
        if (storable == Material.WOOD) {
            return wood;
        } else if (storable == Material.ROCK) {
            return rock;
        } else if (storable == Material.METAL) {
            return metal;
        } else if (storable == Soldier.PRIVATE) {
            return aPrivate;
        } else {
            return emtpy;
        }
    }

    private static Image createImage(String resourcePath) {
        return new Image(
          CarrierSprites.class.getResource(resourcePath).toString(),
          Game.resolution, 0, true, false, true);
    }

    /**
     * Gets correct Carrier Sprite
     *
     * @param color Color of the Carrier
     * @param direction Direction of the Carrier
     * @param storable Item the Carrier is holding
     * @return Carrier Sprite
     */
    public static Image getCarrierSprite(PlayerColor color, LinkDirection direction, Storable storable) {
        return switch (color) {
            case RED -> RED.getCarrierSprite(direction, storable);
            case GREEN -> GREEN.getCarrierSprite(direction, storable);
            case BLUE -> BLUE.getCarrierSprite(direction, storable);
            case YELLOW -> YELLOW.getCarrierSprite(direction, storable);
            case NONE -> null;
        };
    }

    /**
     * Private Constructor
     * Class not instatiable
     */
    private CarrierSprites() {}
}
