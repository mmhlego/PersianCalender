package Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.CustomMonth;
import Model.JalaliCalendar;
import Model.SpecialDays;
import Model.JalaliCalendar.YearMonthDate;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainPage implements Initializable {
    @FXML
    private HBox Background;
    @FXML
    private Label MonthSpecialDaysLBL;
    @FXML
    private VBox MonthSpecialDaysVBox;
    @FXML
    private Button NextMonthBTN;
    @FXML
    private Label CurrentMonthLBL;
    @FXML
    private Button PrevMonthBTN;
    @FXML
    private GridPane MonthDaysGrid;
    @FXML
    private ImageView ExitBTN;
    @FXML
    private ImageView DragBTN;

    CustomMonth CurrentMonth;
    int CurrentDay;

    private double OffsetX, OffsetY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CalcCurrentMonth();
        ShowMonth();
        MakeMovable();

        PrevMonthBTN.setOnMouseClicked(e -> ChangeMonth(-1));
        NextMonthBTN.setOnMouseClicked(e -> ChangeMonth(1));
        ExitBTN.setOnMouseClicked(e -> System.exit(0));
    }

    private void CalcCurrentMonth() {
        LocalDate today = LocalDate.now();
        YearMonthDate todayG = new YearMonthDate(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
        YearMonthDate todayJalali = JalaliCalendar.gregorianToJalali(todayG);
        int year = todayJalali.getYear();
        int month = todayJalali.getMonth();
        CurrentDay = todayJalali.getDate();
        int dayOfWeek = today.minusDays(CurrentDay - 1).getDayOfWeek().getValue();

        CurrentMonth = new CustomMonth(year, month, dayOfWeek);
    }

    private void MakeMovable() {
        DragBTN.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                OffsetX = ((Stage) DragBTN.getParent().getParent().getScene().getWindow()).getX() - event.getScreenX();
                OffsetY = ((Stage) DragBTN.getParent().getParent().getScene().getWindow()).getY() - event.getScreenY();
            }
        });

        DragBTN.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ((Stage) DragBTN.getParent().getParent().getScene().getWindow()).setX(event.getScreenX() + OffsetX);
                ((Stage) DragBTN.getParent().getParent().getScene().getWindow()).setY(event.getScreenY() + OffsetY);
            }
        });
    }

    private void ShowMonth() {
        MonthDaysGrid.getChildren().clear();

        System.out.println(
                CurrentMonth.getYear() + " - " + CurrentMonth.getMonth() + " - ws: " + CurrentMonth.getWeekStart());

        CurrentMonthLBL.setText(CurrentMonth.GetPersianForm() + " " + CurrentMonth.getYear());

        if (CurrentMonth.getWeekStart() >= 6) {
            CurrentMonth.setWeekStart(CurrentMonth.getWeekStart() - 7);
        }

        for (int i = 0; i < CurrentMonth.getDays(); i++) {
            VBox box = new VBox();
            box.setMinWidth(60);
            box.setMinHeight(52);
            box.setStyle(
                    "-fx-border-color:#769FCD; -fx-background-color:#B9D7EA; -fx-border-radius:10; -fx-background-radius:10;");

            Label label = new Label(Integer.toString(i + 1));
            label.setMinWidth(60);
            label.setMinHeight(52);
            label.setStyle("-fx-alignment:center; -fx-cursor:hand; -fx-text-fill:black; ");

            label.setOnMouseClicked(e -> Select(box));

            box.getChildren().addAll(label);

            ((Label) box.getChildren().get(0)).setText(Integer.toString(i + 1));
            MonthDaysGrid.add(box, (i + CurrentMonth.getWeekStart() + 1) % 7,
                    (i + CurrentMonth.getWeekStart() + 1) / 7);
        }

        ShowSpecialDays(CurrentDay);

        Select((VBox) MonthDaysGrid.getChildren().get(CurrentDay - 1));
    }

    private void ShowSpecialDays(int day) {
        int monthIndex = CurrentMonth.getMonth() - 1;

        MonthSpecialDaysVBox.getChildren().clear();

        for (int i = 0; i < CurrentMonth.getDays(); i++) {
            if (SpecialDays.days[monthIndex][i] != null) {
                for (String st : SpecialDays.days[monthIndex][i].split("-")) {
                    Label label = new Label(
                            "    " + Integer.toString(i + 1) + " " + CurrentMonth.GetPersianForm() + ": " + st);
                    label.setMinHeight(30);

                    if (day == i + 1) {
                        label.setStyle("-fx-font-weight: 800;");
                        label.setFont(new Font("B Koodak Bold", 16));
                        label.setText("  " + label.getText());
                    } else {
                        label.setFont(new Font("B Kamran", 21));
                        label.setOnMouseEntered(e -> {
                            label.setMinHeight(35);
                            label.setFont(new Font("B Kamran", 23));
                        });
                        label.setOnMouseExited(e -> {
                            label.setMinHeight(30);
                            label.setFont(new Font("B Kamran", 21));
                        });
                    }

                    MonthSpecialDaysVBox.getChildren().add(label);
                }
            }
        }
    }

    private void Select(VBox box) {
        for (Node node : MonthDaysGrid.getChildren()) {
            node.setStyle(
                    "-fx-border-color:#769FCD; -fx-background-color:#B9D7EA; -fx-border-radius:10; -fx-background-radius:10;");
            ((VBox) node).getChildren().get(0).setStyle("-fx-alignment:center; -fx-cursor:hand; -fx-text-fill:black; ");
        }

        box.setStyle(
                "-fx-border-color:#769FCD; -fx-background-color:#769FCD; -fx-border-radius:10; -fx-background-radius:10;");
        box.getChildren().get(0).setStyle("-fx-alignment:center; -fx-cursor:hand; -fx-text-fill:white; ");

        ShowSpecialDays(MonthDaysGrid.getChildren().indexOf(box) + 1);
    }

    private void ChangeMonth(int addValue) {
        CurrentMonth = new CustomMonth(CurrentMonth.getYear(), CurrentMonth.getMonth() + addValue,
                NextWeekStart(addValue));

        FadeTransition fade = new FadeTransition(Duration.millis(500), MonthDaysGrid);
        fade.setFromValue(10);
        fade.setToValue(0);
        fade.play();

        ShowMonth();

        FadeTransition fade2 = new FadeTransition(Duration.millis(500), MonthDaysGrid);
        fade2.setFromValue(0);
        fade2.setToValue(10);
        fade2.play();
    }

    private int NextWeekStart(int addValue) {
        if (addValue == 1) {
            return (CurrentMonth.getWeekStart() + CurrentMonth.getDays() - 1) % 7 + 1;
        } else if (addValue == -1) {
            int days = new CustomMonth(CurrentMonth.getYear(), CurrentMonth.getMonth() - 1, 1).getDays();
            return (CurrentMonth.getWeekStart() - days + 35 - 1) % 7 + 1;
        }
        return 0;
    }

    //    private void MakeDraggable(Label label) {
    //        label.setOnMousePressed(new EventHandler<MouseEvent>() {
    //            @Override
    //            public void handle(MouseEvent event) {
    //                MousePressed(event);
    //                label.setStyle("-fx-cursor: closed_hand;");
    //            }
    //        });
    //
    //        label.setOnMouseReleased(new EventHandler<MouseEvent>() {
    //            @Override
    //            public void handle(MouseEvent event) {
    //                label.setStyle("-fx-cursor: none;");
    //            }
    //        });
    //
    //        label.setOnMouseDragged(new EventHandler<MouseEvent>() {
    //            @Override
    //            public void handle(MouseEvent event) {
    //                MouseDragged(event);
    //            }
    //        });
    //    }
    //
    //    private void MousePressed(MouseEvent event) {
    //        OffsetX = ((Stage) MinimizeBTN.getParent().getParent().getScene().getWindow()).getX() - event.getScreenX();
    //        OffsetY = ((Stage) MinimizeBTN.getParent().getParent().getScene().getWindow()).getY() - event.getScreenY();
    //    }
    //
    //    private void MouseDragged(MouseEvent event) {
    //        ((Stage) MinimizeBTN.getParent().getParent().getScene().getWindow()).setX(event.getScreenX() + OffsetX);
    //        ((Stage) MinimizeBTN.getParent().getParent().getScene().getWindow()).setY(event.getScreenY() + OffsetY);
    //
    //        if (event.getScreenY() == 0.0) {
    //            MaximizeWindow();
    //        }
    //    }

}
