package View_Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyChoiceReportController implements Initializable {
    @FXML
    private TableView<?> contactScheduleReportTableView;

    @FXML
    private TableColumn<?, Integer> cSRAppointmentIDCol;

    @FXML
    private TableColumn<?, String> cSRTitleCol;

    @FXML
    private TableColumn<?, String> cSRTypeCol;

    @FXML
    private TableColumn<?, String> cSRDescriptionCol;

    @FXML
    private TableColumn<?, String> cSRStartCol;

    @FXML
    private TableColumn<?, String> cSREndCol;

    @FXML
    private TableColumn<?, Integer> cSRCustomerIDCol;

    @FXML
    private Button backAppointmentScheduleBtn;

    @FXML
    private ComboBox<?> cSRContactNameCBox;

    @FXML
    private CommonController commonController = new CommonController();

    @FXML
    void handleBackAppointmentSchedule(ActionEvent event) throws IOException {
        commonController.handleAppointmentSchedule(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
