module chatgptclone {
    requires javafx.controls;
    requires javafx.fxml;
    requires langchain4j.open.ai;
    requires langchain4j.core;

    opens chatgptclone to javafx.fxml;
    exports chatgptclone;
}
