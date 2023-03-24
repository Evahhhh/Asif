package com.example.asif.utils;
import java.util.ArrayList;
import java.util.UUID;

public class UUIDUtils {
    private static final ArrayList<UUID> uuidList = new ArrayList<>();

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

    private static boolean isUUIDAlreadyExist(UUID uuid) {
        for (UUID existingUUID : uuidList) {
            if (existingUUID.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

}
