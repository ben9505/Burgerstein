package theagency.vn.listenner;

import android.util.SparseArray;

import java.util.ArrayList;

/**
 * Created by ben on 15/02/2018.
 */

public class NotificationCenter {

    private static int num = 1;
    public static final int HOME = num++;
    public static final int SYMTOM = num++;
    public static final int NAHRSTOFF = num++;
    public static final int SENDEMAIL = num++;
    public static final int CONTACTINFO = num++;
    public static final int UBER = num++;
    public static final int EMGAGEMENT = num++;
    public static final int SYMTOM_NARSTOFF = num++;
    public static final int LINK = num++;

    // as HashMap<Integer, Object>
    // HashMap generally faster
    // SparseArray less memories
    private SparseArray<ArrayList<Object>> observers = new SparseArray<>();

    public interface NotificationCenterDelegate {
        void didReceivedNotification(int id, Object... args);
    }
    // volatile always update value in all threads when have changed
    private static volatile NotificationCenter Instance = null;

    public static NotificationCenter getInstance() {
        NotificationCenter localInstance = Instance;
        if (localInstance == null) {
            // use synchronized to sure each thread connect to this class in one time. one by one
            synchronized (NotificationCenter.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new NotificationCenter();
                }
            }
        }
        return localInstance;
    }

    public void postNotificationName(int id, Object... args) {

        ArrayList<Object> objects = observers.get(id);
        if (objects != null && !objects.isEmpty()) {
            for (int a = 0; a < objects.size(); a++) {
                Object obj = objects.get(a);
                ((NotificationCenterDelegate) obj).didReceivedNotification(id, args);
            }
        }
    }

    public void addObserver(Object observer, int id) {
        ArrayList<Object> objects = observers.get(id);
        if (objects == null) {
            observers.put(id, (objects = new ArrayList<>()));
        }
        if (objects.contains(observer)) {
            return;
        }
        objects.add(observer);
    }

    public void removeObserver(Object observer, int id) {
        ArrayList<Object> objects = observers.get(id);
        if (objects != null) {
            objects.remove(observer);
        }
    }


}
