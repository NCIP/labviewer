Setting up the Eclipse IDE for Lab Viewer
----------------------------------------
- before setting up eclipse, please perform a build from build>ant dist:installer
- Set up a variable in the Eclipse. Go to Preferences->Java->Build Path->Classpath Variables
- Click New

- Enter the following names and paths 
    
    ----------------------------------------------------------------------------------------------------------------------------------
    Name                | Path                                                   | example
    ----------------------------------------------------------------------------------------------------------------------------------
    LAB_VIEWER_LIB 	| <lab viewer home folder>/ctomlabviewer                 | D:/ccts/lab-viewer-br/ctomlabviewer
    CTOM_PERSIST_LIB    | <lab viewer home folder>/CTOMDataPersistence-PG/target | D:/ccts/lab-viewer-br/CTOMDataPersistence-PG/target
    LAB_LOADER_LIB      | <lab viewer home folder>/LabLoader-1.3/lib             | D:/ccts/lab-viewer-br/LabLoader-1.3/lib
    GLOBUS_LIB          | <cagrid home folder>ws-core-4.0.3/lib                  | D:/cagrid1.3/ws-core-4.0.3/lib

- Create a project from exisiting source and provide the location for labviewer 