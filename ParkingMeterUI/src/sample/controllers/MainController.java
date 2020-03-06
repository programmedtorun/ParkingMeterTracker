package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.datamodel.ParkingMeter;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    @FXML
    private ListView<ParkingMeter> pmListView;

    @FXML
    private Button meterListButton;
    private ObservableList<ParkingMeter> parkingMeters;

    @FXML
    private BorderPane mainBorderPane;

    public void initialize(){
//        pmListView.getItems().setAll(ParkingMeterData.getInstance().getParkingMeters());
//        pmListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void showMeterList(){
        // only be able to interact when dialog is open default is modiel:
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/parkingmeterlist.fxml"));
            Parent root = fxmlLoader.load();
            stage.setTitle("PM List");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e){
            System.out.println("Could not load new window...");
            e.printStackTrace();
        }
    }

    @FXML
    public void showNewPMDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Create New Parking Meter");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../views/newmeterdialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Couldn't load the dialog: ");
            e.printStackTrace();
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            NewMeterController ctrl = fxmlLoader.getController();
            ctrl.createNewMeter();
        }
    }




    //
//    @FXML
//    private ListView<TodoItem> todoListView;
//
//    private List<TodoItem> todoItems;
//
//    @FXML
//    private TextArea itemDeteilsTextArea;
//
//    @FXML
//    private Label deadlineLabel;
//
//    @FXML
//    private BorderPane mainBorderPane;
//
//    @FXML
//    private ContextMenu listContextMenu; //will associate context menu with cell factory
//
//    private Predicate<TodoItem> wantAllItems;
//    private Predicate<TodoItem> wantTodaysItems;
//
//    public void initialize(){
//        listContextMenu = new ContextMenu();
//        MenuItem deleteMenuItem = new MenuItem("Delete");
//        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//                deleteItem(item);
//            }
//        });
//        // provide list with items we want to filter:
//
//        wantTodaysItems = new Predicate<TodoItem>() {
//            @Override
//            public boolean test(TodoItem todoItem) {
//                return todoItem.getDeadline().equals(LocalDate.now());
//            }
//        };
//        wantAllItems = new Predicate<TodoItem>() {
//            @Override
//            public boolean test(TodoItem todoItem) {
//                return true;
//            }
//        };
//
//
//
//        filteredList = new FilteredList<TodoItem>(TodoData.getInstance().getTodoItems(), wantAllItems);
//
//        SortedList<TodoItem> sortedList = new SortedList<TodoItem>(filteredList,
//                new Comparator<TodoItem>() {
//                    @Override
//                    public int compare(TodoItem o1, TodoItem o2) {
//                        // pass in TodoItems to be compared. returns a neg val
//                        // if obj o1 is LESS than obj o2. returns zero if
//                        // two objs are considered to be EQUAL. must return POSITIVE
//                        // value if obj o1 is GREATER than o2.
//                        if(o1.getDeadline().isBefore(o2.getDeadline())) {
//                            return -1;
//                        } else if(o1.getDeadline().equals(o2.getDeadline())){
//                            return 0;
//                        } else {
//                            return 1;
//                        }
//                    }
//                });
//
//        listContextMenu.getItems().addAll(deleteMenuItem); //adding delete menu item to context menu
//        todoListView.setItems(sortedList); // KEY LINE!!! bind list view to observable list in tododata
//        todoListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        selectItem(TodoData.getInstance().getTodoItems().get(0));
//        todoListView.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
//            @Override
//            public ListCell<TodoItem> call(ListView<TodoItem> param) {
//                ListCell<TodoItem> cell = new ListCell<TodoItem>(){
//                    @Override
//                    protected void updateItem(TodoItem item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if(empty){
//                            setText(null);
//                        }else{
//                            setText(item.getShortDescription());
//                            if(item.getDeadline().isBefore(LocalDate.now().plusDays(1))){
//                                setTextFill(Color.RED);
//                            } else if(item.getDeadline().equals(LocalDate.now().plusDays(1))){
//                                setTextFill(Color.ORANGE);
//                            }
//                        }
//                    }
//                };
//
//                cell.emptyProperty().addListener(
//                        (obs, wasEmpty, isNowEmpty) -> {
//                            if (isNowEmpty){
//                                cell.setContextMenu(null);
//                            } else {
//                                cell.setContextMenu(listContextMenu);
//                            }
//                        }
//                );
//                return cell;
//            }
//        });
//    }
//

    //    private FilteredList<TodoItem> filteredList;
    //
    //    private ToggleButton filterToggleButton;
//    @FXML
//

//
//    @FXML
//    public void handleKeyPress(KeyEvent event){
//        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
//        if(selectedItem != null){
//            if(event.getCode().equals(KeyCode.BACK_SPACE)){
//                deleteItem(selectedItem);
//            }
//        }
//    }
//
//    @FXML
//    public void handleClickListView(){
//        TodoItem item = todoListView.getSelectionModel().getSelectedItem();
//        selectItem(item);
//
//    }
//    public void deleteItem(TodoItem item){
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Delete TODO Item");
//        alert.setHeaderText("Delete Item: " + item.getShortDescription());
//        alert.setContentText("Are you sure? Click OK.");
//        Optional<ButtonType> result = alert.showAndWait();
//        if(result.isPresent() && result.get() == ButtonType.OK){
//            TodoData.getInstance().deleteTodoItem(item);
//        }
//    }
//
//    @FXML
//    public void handleFilterButton(){
//        TodoItem selectedItem = todoListView.getSelectionModel().getSelectedItem();
//        if(filterToggleButton.isSelected()){
//            // provide filtering criteria for filtered list
//            filteredList.setPredicate(wantTodaysItems);
//            if(filteredList.isEmpty()){
//                itemDeteilsTextArea.clear();
//                deadlineLabel.setText("");
//            } else if(filteredList.contains(selectedItem)){
//                todoListView.getSelectionModel().select(selectedItem);
//            } else {
//                todoListView.getSelectionModel().selectFirst();
//            }
//        } else {
//            filteredList.setPredicate(wantAllItems);
//            todoListView.getSelectionModel().select(selectedItem);
//
//        }
//    }
//
//    @FXML
//    public void handleExit(){
//        Platform.exit();
//    }






}
