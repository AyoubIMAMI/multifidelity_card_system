package fr.polytech.interfaces.advantage;

import fr.polytech.exceptions.advantage.AdvantageNotFoundException;
import fr.polytech.entities.Advantage;

public interface AdvantageModifier {
    Advantage createAdvantage(Advantage advantage);
    void deleteAdvantage(Long advantageID) throws AdvantageNotFoundException;
}
