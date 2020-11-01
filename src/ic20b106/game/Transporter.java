package ic20b106.game;

import ic20b106.game.buildings.Material;

public class Transporter implements Runnable {

    public Transporter(Material cargo) {
        this.cargo = cargo;
    }

    @Override
    public void run() {

    }

    public Material getCargo() {
        return cargo;
    }

    private Material cargo;
}
