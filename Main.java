import java.util.List;
import java.utilgit branch -M main.ArrayList;

// Шаблон "Фабричний метод"
abstract class TeamMember {
    String name;

    public TeamMember(String name) {
        this.name = name;
    }

    public abstract void performTask(String task);
}

class Developer extends TeamMember {
    private List<String> skills;

    public Developer(String name, List<String> skills) {
        super(name);
        this.skills = skills;
    }

    @Override
    public void performTask(String task) {
        System.out.println("Розробник " + name + " виконує завдання: " + task + " (Навички: " + String.join(", ", skills) + ")");
    }
}

class Tester extends TeamMember {
    private int experience;

    public Tester(String name, int experience) {
        super(name);
        this.experience = experience;
    }

    @Override
    public void performTask(String task) {
        System.out.println("Тестувальник " + name + " тестує: " + task + " (Досвід: " + experience + " роки)");
    }
}

class ProjectManager extends TeamMember {
    private int projects;

    public ProjectManager(String name, int projects) {
        super(name);
        this.projects = projects;
    }

    @Override
    public void performTask(String task) {
        System.out.println("Проектний менеджер " + name + " планує: " + task + " (Кількість проєктів: " + projects + ")");
    }
}

class TeamMemberFactory {
    public static TeamMember createMember(String role, String name, Object... args) {
        switch (role.toLowerCase()) {
            case "developer":
                return new Developer(name, (List<String>) args[0]);
            case "tester":
                return new Tester(name, (int) args[0]);
            case "pm":
                return new ProjectManager(name, (int) args[0]);
            default:
                throw new IllegalArgumentException("Невідома роль!");
        }
    }
}

// Шаблон "Медіатор"
class ProjectMediator {
    private List<TeamMember> members;

    public ProjectMediator() {
        this.members = new ArrayList<>();
    }

    public void addMember(TeamMember member) {
        members.add(member);
    }

    public void assignTask(String task) {
        for (TeamMember member : members) {
            member.performTask(task);
        }
    }
}

// Шаблон "Спостерігач"
interface Observer {
    void update(String message);
}

class TeamObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("Сповіщення: " + message);
    }
}

class Project {
    private List<Observer> observers;

    public Project() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

// Основний клас
public class ITTeamSimulator {
    public static void main(String[] args) {
        // Фабрика
        TeamMemberFactory factory = new TeamMemberFactory();

        // Медіатор
        ProjectMediator mediator = new ProjectMediator();

        // Проект
        Project project = new Project();

        // Спостерігач
        TeamObserver observer = new TeamObserver();
        project.addObserver(observer);

        // Додавання членів команди
        TeamMember dev = factory.createMember("developer", "Іван", List.of("Java", "Spring"));
        TeamMember tester = factory.createMember("tester", "Марія", 5);
        TeamMember pm = factory.createMember("pm", "Олександр", 10);

        mediator.addMember(dev);
        mediator.addMember(tester);
        mediator.addMember(pm);

        // Додавання задач
        project.notifyObservers("Новий проект розпочато!");
        mediator.assignTask("Розробка основного модуля");
        mediator.assignTask("Тестування модуля");
        mediator.assignTask("Планування наступного етапу");
    }
}
