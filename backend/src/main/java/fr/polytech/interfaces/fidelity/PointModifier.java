package fr.polytech.interfaces.fidelity;

import fr.polytech.entities.FidelityAccount;

public interface PointModifier {
    //points computed from the price
    void incrementPoints(FidelityAccount fidelityAccount, float price);
    void decrementPoints(FidelityAccount fidelityAccount, int points);
}
