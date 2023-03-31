package com.example.asif.utils;
import java.util.ArrayList;
import java.util.UUID;

/**
 * A utility class for generating and managing UUIDs (Universally Unique Identifiers).
 */
public class UUIDUtils {
    /**
     * A list to store all previously generated UUIDs.
     */
    private static final ArrayList<UUID> uuidList = new ArrayList<>();

    /**
     * Generates a new UUID and ensures its uniqueness by checking if it already exists in the uuidList.
     * @return a new, unique UUID.
     */
    public static UUID getNewId() {
        boolean idExists = false;
        UUID id;
        do {
            id = UUID.randomUUID();
            System.out.println(id.toString()); // imprime l'identifiant unique généré
            if (isUUIDAlreadyExist(id)) idExists = true;
            else idExists = false;
        } while (idExists);
        //ajouter à la liste d'ids déjà utilisés
        uuidList.add(id);
        return id;
    }

    /**
     * Checks if a given UUID already exists in the uuidList.
     * @param uuid the UUID to check.
     * @return true if the UUID already exists in the list, false otherwise.
     */
    private static boolean isUUIDAlreadyExist(UUID uuid) {
        for (UUID existingUUID : uuidList) {
            if (existingUUID.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

}
