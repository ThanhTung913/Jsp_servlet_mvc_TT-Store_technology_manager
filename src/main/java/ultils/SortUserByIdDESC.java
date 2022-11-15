package ultils;

import model.User;

import java.util.Comparator;

public class SortUserByIdDESC implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        if (o2.getId() - o1.getId() > 0) {
            return 1;
        } else if (o2.getId() - o1.getId() < 0) {
            return -1;
        }
        return 0;
    }
}
