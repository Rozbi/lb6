package lib.utility;

import server.exeptions.InvalidInputException;
import server.managers.CollectionManager;
import lib.managers.InputManager;
import lib.managers.OutputManager;
import lib.spaceMarine.*;

import java.util.NoSuchElementException;

/**
 * Класс чтения объекта
 */
public class Ask {
    private InputManager inputManager;
    private OutputManager outputManager;

    public Ask(InputManager inputManager, OutputManager outputManager) {
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }
    /*private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long health; //Значение поля должно быть больше 0
    private int heartCount; //Значение поля должно быть больше 0, Максимальное значение поля: 3
    private AstartesCategory category; //Поле может быть null
    private MeleeWeapon meleeWeapon; //Поле может быть null
    private Chapter chapter; //Поле может быть null*/

    public SpaceMarine getSpaceMarine(OutputManager outputManager, CollectionManager collectionManager, InputManager inputManager) {
        while (true) {
            try {
                outputManager.print("Введите имя: ");
                String name;
                while (true) {
                    name = inputManager.read();
                    if (!name.isEmpty()) {
                        break;
                    }
                    outputManager.println("Имя не может быть пустым или null! ");
                    outputManager.print("Введите имя: ");
                }
                Coordinates coordinates = getUzerCoordinates(outputManager);
                outputManager.print("Введите здоровье: ");
                Long health;
                while (true) {
                    var line = inputManager.read();
                    try {
                        health = Long.parseLong(line);
                        if (health > 0) break;
                        else {
                            outputManager.println("Поле должно быть больше 0 ");
                        }
                    } catch (NumberFormatException e) {
                        outputManager.println("Поле должно быть не пустым формата Long ");
                    }
                    outputManager.print("Введите здоровье: ");
                }
                outputManager.print("Введите пульс от 0 до 3 ");
                int heartCount;
                while (true) {
                    var line = inputManager.read();
                    try {
                        heartCount = Integer.parseInt(line);
                        if ((heartCount > 0) && (heartCount <= 3)) break;
                        else {
                            outputManager.println("Поле должно быть от 0 до 3 ");
                        }
                    } catch (NumberFormatException e) {
                        outputManager.println("Поле должно быть не пустым формата int ");
                    }
                    outputManager.print("Введите пульс от 0 до 3 ");
                }
                AstartesCategory astartesCategory = getUserAstartesCategory(outputManager);
                MeleeWeapon meleeWeapon = getUserMeleeWeapon(outputManager);
                Chapter chapter = getUserChapter(outputManager);
                return new SpaceMarine(collectionManager.getCurrentId(), name, coordinates, health, heartCount, astartesCategory, meleeWeapon, chapter);
            } catch (NoSuchElementException | IllegalStateException e) {
                outputManager.print("Ошибка чтения ");
            } catch (InvalidInputException e) {
                outputManager.print("Неправильный ввод данных ");
            }
        }
    }

    public Coordinates getUzerCoordinates(OutputManager outputManager) {
        try {
            /*
            private Long x; //Значение поля должно быть больше -42, Поле не может быть null
            private float y; //Значение поля должно быть больше -359, Поле не может быть null
             */
            Long x;
            outputManager.print("Введите координату x: ");
            while (true) {
                var line = inputManager.read().trim();
                if (!line.isEmpty()) {
                    try {
                        x = Long.parseLong(line);
                        break;
                    } catch (NumberFormatException e) {
                        outputManager.println("Поле должно быть Long ");
                        outputManager.print("Введите координату x: ");
                    }
                } else {
                    outputManager.println("Поле не может быть пустым или null!");
                    outputManager.print("Введите координату x:");
                }
            }
            outputManager.print("Введите координату y: ");
            float y;
            while (true) {
                var line = inputManager.read().trim();
                if (!line.isEmpty()) {
                    try {
                        y = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException e) {
                        outputManager.println("Поле должно быть float ");
                        outputManager.print("Введите координату y: ");
                    }
                } else {
                    outputManager.println("Поле не может быть пустым или null! ");
                    outputManager.print("Введите координату y: ");
                }
            }
            return new Coordinates(x, y);
        } catch (InvalidInputException ex) {
            outputManager.println("Неправильный ввод данных ");
            return null;
        }
    }

 public MeleeWeapon getUserMeleeWeapon(OutputManager outputManager) {
        try {
            outputManager.print("MeleeWeapon (" + MeleeWeapon.names() + "): ");
            MeleeWeapon r;
            while (true) {
                var line = inputManager.read();
                if (line.isEmpty()) {
                    r = null;
                    break;
                } else {
                    try {
                        r = MeleeWeapon.valueOf(line.toUpperCase());
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {
                        outputManager.print("Выберите значение из списка ");
                        outputManager.print("MeleeWeapon (" + MeleeWeapon.names() + "): ");
                    }
                }
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            outputManager.print("Ошибка чтения данных");
        } catch (InvalidInputException e) {
            outputManager.print("Неправильный ввод данных ");
        }
        return null;
    }

    public AstartesCategory getUserAstartesCategory(OutputManager outputManager) {
        try {
            outputManager.print("AstartesCategory (" + AstartesCategory.names() + "): ");
            AstartesCategory r;
            while (true) {
                var line = inputManager.read();
                if (line.isEmpty()) {
                    r = null;
                    break;
                } else {
                    try {
                        r = AstartesCategory.valueOf(line.toUpperCase());
                        break;
                    } catch (NullPointerException | IllegalArgumentException e) {
                        outputManager.print("Выберите значение из списка ");
                        outputManager.print("AstartesCategory (" + AstartesCategory.names() + "): ");
                    }
                }
            }
            return r;
        } catch (NoSuchElementException | IllegalStateException e) {
            outputManager.print("Ошибка чтения данных");
        } catch (InvalidInputException e) {
            outputManager.print("Неправильный ввод данных ");
        }
        return null;
    }

    public Chapter getUserChapter(OutputManager outputManager) throws InvalidInputException {
        /*private String name; //Поле может быть null, Строка может быть пустой
        private String world; //Поле может быть null*/
        try {
            outputManager.print("Введите название главы: ");
            String x = inputManager.read();
            if (x.isEmpty()) {
                x = null;
                return null;
            } else {
                String y;
                outputManager.print("Введите мир нынешней главы: ");
                y = inputManager.read();
                return new Chapter(x, y);
            }
        } catch (InvalidInputException e) {
            outputManager.print("Ошибка чтения данных");
        }
        return null;
    }
}