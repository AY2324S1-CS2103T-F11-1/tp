---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point).

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `CCACommanderParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `CcaCommanderParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `CreateMemberCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `CreateMemberCommand`) which the `CcaCommanderParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `CreateMemberCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the CCACommander data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `CcaCommander`, which `Person` references. This allows `CcaCommander` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both CCACommander data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `CcaCommanderStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.ccacommander.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Enrol Feature
The enrol feature allows users to link a member to an event in order to keep track of their attendance and contributions to the event.

This section will explain how the enrol feature was implemented and the various design considerations when implementing the feature.

#### Implementation
The enrollment mechanism is facilitated by `EnrolCommand`. It extends `Command`.
The method, `EnrolCommand#execute()`, performs a validity check and adds a member's attendance for a particular event if
all the supplied parameters are valid.

The sequence diagram below shows how the `Model` and `LogicManager` components interact when a `EnrolCommand` is executed with user input
`enrol m/1 e/1 h/1 r/Role: Photographer` represented by `...`

![EnrolSequenceDiagram](images/EnrolSequenceDiagram.png)

1. `LogicManager` uses the `CcaCommanderParser` class to parse the user command, creating a new instance of `EnrolCommandParser` object.
2. The `EnrolCommandParser` creates a new instance of a `EnrolCommand` object and returns it to `CcaCommanderParser`
1. `CcaCommanderParser` encapsulates the `EnrolCommand` object as a `Command` object which is executed by
   the `LogicManager`.
1. The command execution calls `Model#getFilteredMemberList()` and `Model#getFilteredEventList()` to get the desired `member` and
   `event` respectively using indexes supplied by the user.
1. Next, the command execution creates a new instance of an `Attendance` object.
1. `Model#createAttendance()` is then called, adding the new `Attendance` object to the `uniqueAttendanceList` object in `model`.
1. The change resulting from the command's execution is saved using the `Model#commit()` method for the `undo`/`redo` feature.
1. A `CommandResult` object which encapsulates the result of the command execution is passed back to the `Ui`.

The following activity diagram shows how the `EnrolCommand` works.

![EnrolActivityDiagram](images/EnrolActivityDiagram.png)

#### Design Considerations

##### Aspect 1: `EnrolCommand` parameters

* **Alternative 1 (current choice):** `EnrolCommand` has parameters `Name memberName`, `Name eventName`, `Hours hours` and `Remark remark`.
    * Pros:
        * Less complex code reduces the possibility of bugs.
        * Reduced coupling, enhancing maintainability
    * Cons:
        * Not standardised with other creation commands.

* **Alternative 2:** `EnrolCommand` has parameter `Attendance attendance`
    * Pros:
        * Standardised with other creation commands
    * Cons:
        * More complex code leading to higher possibility of bugs.
        * Increased coupling, reducing maintainability.

Alternative 1 was chosen because the cons of implementing alternative 2 outweighs the benefits derived from it. Alternative 2
would require `EnrolCommandParser` to have knowledge of the `Model`. This would result in an increased coupling as it would
then have greater dependence on more modules. Hence, while Alternative 1 would result in the `EnrolCommand` to be not standardised
with other create commands, it provides a simpler implementation with fewer changes needed while also maintaining ease of testing.

### Undo/Redo Feature

The Undo/Redo feature allows the user to revert commands that were entered wrongly.

This section will explain how the redo and undo features were implemented and the various design considerations when implementing the feature.

#### Implementation

The undo/redo mechanism is facilitated by `VersionedCcaCommander`. It extends `CcaCommander` with an undo/redo history, stored internally as an `ccaCommanderVersionList` and `versionPointer`. Additionally, it implements the following operations:

* `VersionedCcaCommander#commit(String commitMessage)` — Saves the current CCACommander version and its commit message in its history.
* `VersionedCcaCommander#undo()` — Restores the previous CCACommander version from its history.
* `VersionedCcaCommander#redo()` — Restores a previously undone CCACommander version from its history.

These operations are exposed in the `Model` interface as `Model#commit()`, `Model#undo()` and `Model#redo()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedCcaCommander` will be initialized with the initial CCACommander version, and the `versionPointer` pointing to that single CCACommander version. This initial CCACommander version will be saved into the `ccaCommanderVersionList`

![UndoRedoVersion0](images/UndoRedoVersion0.png)

Step 2. The user executes `deleteMember 5` command to delete the 5th member in CCACommander. The `deleteMember` command calls `Model#commit(String commitMessage)`, causing the modified version of the CCACommander after the `deleteMember 5` command executes to be saved in the `ccaCommanderVersionList`, and the `versionPointer` is shifted to the newly inserted CCACommander version.

![UndoRedoVersion1](images/UndoRedoVersion1.png)

Step 3. The user executes `createMember n/David …​` to add a new member. The `createMember` command also calls `Model#commit(String commitMessage)`, causing another modified CCACommander version to be saved into the `ccaCommanderVersionList`.

![UndoRedoVersion2](images/UndoRedoVersion2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commit(String commitMessage)`, so the CCACommander version will not be saved into the `ccaCommanderVersionList`.

</div>

Step 4. The user now decides that adding the member was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undo()`, which will shift the `versionPointer` once to the left, pointing it to the previous CCACommander version, and restores the CCACommander to that version.

![UndoRedoVersion3](images/UndoRedoVersion3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `versionPointer` is at index 0, pointing to the initial CcaCommander version, then there are no previous CcaCommander versions to restore. The `undo` command uses `Model#canUndo()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redo()`, which shifts the `versionPointer` once to the right, pointing to the previously undone version, and restores CCACommander to that version.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `versionPointer` is at index `ccaCommanderVersionList.size() - 1`, pointing to the latest CCACommander version, then there are no undone CcaCommander versions to restore. The `redo` command uses `Model#canRedo()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the CCACommander, such as `list`, will usually not call `Model#commit(String commitMessage)`, `Model#undo()` or `Model#redo()`. Thus, the `ccaCommanderVersionList` remains unchanged.

![UndoRedoVersion4](images/UndoRedoVersion4.png)

Step 6. The user executes `clear`, which calls `Model#commit(String commitMessage)`. Since the `versionPointer` is not pointing at the end of the `ccaCommanderVersionList`, all CCACommander versions after the `versionPointer` will be purged. Reason: It no longer makes sense to redo the `createMember n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoVersion5](images/UndoRedoVersion5.png)

The following activity diagram summarizes what happens when a user executes a new command:

![CommitActivityDiagram](images/CommitActivityDiagram.png)

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire CCACommander.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the member being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.


### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a significant number of CCA members and events
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**: one stop app to manage CCA members and events faster than a typical mouse/GUI driven app


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​       | I can …​                                    | So that …​                                                                        |
|----------|---------------|---------------------------------------------|-----------------------------------------------------------------------------------|
| `* * *`  | beginner user | create a new profile of a CCA member        | I can keep track of their information                                             |
| `* * *`  | power user    | delete the profile of a CCA member          | I can remove them from the system when needed                                     |
| `* * *`  | beginner user | list all members in my CCA                  | I can keep track of my CCA strength                                               |
| `* * *`  | beginner user | add a new event hosted by the CCA           | I can keep track of upcoming activities                                           |
| `* * *`  | power user    | delete an event                             | I can remove it from the schedule                                                 |
| `* * *`  | power user    | view a list of all events hosted by the CCA | I can see event history                                                           |
| `* * *`  | beginner user | add a member to an event                    | I can track which members are participating in the event                          |
| `* * *`  | beginner user | remove a member from an event               | I can amend adding the wrong person to an event                                   |
| `* * *`  | beginner user | view the members who attended an event      | I can estimate the number of members who will attend similar events in the future |
| `* * *`  | beginner user | view events of a member                     | I can check how involved that particular member is                                |
| `* * *`  | power user    | undo                                        | I can correct any wrong commands that I have entered previously                   |
| `* * *`  | power user    | redo                                        | I can correct any wrong undo commands that I have entered previously              |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `CCACommander` and the **Actor** is the `user`, unless specified otherwise)

<ins>**Use case: UC01 - Create a member**</ins>

**Guarantees: MSS -> Entered member will be added.**

**MSS**

1.  User enters command to create a member.
2.  CCACommander adds that member to the member list and displays the member added.

    Use case ends.

**Extensions**
* 1a. User uses the command incorrectly.

    * 1a1. CCACommander shows an error message.

      Use case ends.

<ins>**Use case: UC02 - Delete a member**</ins>

**Guarantees: MSS -> Specified member will be deleted.**

**MSS**

1. User requests to <ins>list all members</ins> (UC03).
2. CCACommander shows a list of all members.
3. User requests to delete a specific member in the list of all members.
4. CCACommander deletes the specific member.

    Use case ends.

**Extensions**

* 3a. The given index is invalid.

    * 3a1. CCACommander shows an error message and requests for a valid index from the user.
    * 3a2. User enters new index.
    * Steps 3a1-3a2 are repeated until index given by the user is correct.

      Use case resumes at step 4.

<ins>**Use case : UC03 - List all members**</ins>

**Guarantees: MSS -> All members will be listed.**

**MSS**

1. User requests to list all members.
2. CCACommander lists all members in the CCA.

   Use case ends.

**Extensions**

* 1a. The list of all members is empty.

    * 1a1. CCACommander shows an empty member list.

      Use case ends.

<ins>**Use case : UC04 - List all events**</ins>

**Guarantees: MSS -> All events will be listed.**

**MSS**

1. User requests to list all events.
2. CCACommander lists all events in the CCA.

   Use case ends.

**Extensions**

* 1a. The list of all events is empty.

    * 1a1. CCACommander shows an empty event list.

      Use case ends.

<ins>**Use case: UC05 - View members of event**</ins>

**Guarantees: MSS -> All members of the specified event will be listed.**

**MSS**

1. User requests to <ins>list all events</ins> (UC04).
2. CCACommander shows a list of all events.
3. User requests to view a specific event in the list of all events.
4. CCACommander displays all members of the specified event.

   Use case ends.

**Extensions**
* 3a. The given index is invalid.

    * 3a1. CCACommander shows an error message.

      Use case resumes at step 2.

<ins>**Use case: UC06 - View events of member**</ins>

**Guarantees: MSS -> All events of the specified member will be listed.**

**MSS**

1. User requests to <ins>list all members</ins> (UC03).
2. CCACommander shows a list of all members.
3. User requests to view a specific member in the list of all members.
4. CCACommander displays all events of the specified member.

   Use case ends.

**Extensions**
* 3a. The given index is invalid.

    * 3a1. CCACommander shows an error message.

      Use case resumes at step 2.

<ins>**Use case: UC07 - Create an event**</ins>

**Guarantees: MSS -> Entered event will be added.**

**MSS**

1.  User requests to add an event to the list.
2.  CCACommander adds that event to the event list and displays the event added.

    Use case ends.

**Extensions**
* 1a. User uses the command incorrectly.

    * 1a1. CCACommander shows an error message.

      Use case ends.

<ins>**Use case: UC08 - Delete an event**</ins>

**Guarantees: MSS -> Specified event will be deleted.**

**MSS**

1.  User <ins>lists all events</ins> (UC04).
2.  CCACommander shows a list of all events.
3.  User requests to delete a specific event in the list of all events.
4.  CCACommander deletes the specific event.

    Use case ends.

<ins>**Use case: UC09 - Add member to an event**</ins>

**Guarantees: MSS -> Specified member will be added to the event.**

**MSS**

1. User <ins>lists all members</ins> (UC03).
2. CCACommander shows a list of all members.
3. User <ins>lists all events</ins> (UC04).
4. CCACommander shows a list of all events.
5. User requests to add a specific member in the list of all members to a specific event in the list of all events.
6. CCACommander adds the specified member from the specified event.

    Use case ends.

**Extensions**

* 5a. The given index is invalid.

    * 5a1. CCACommander shows an error message and requests for a valid index from the user.
    * 5a2. User enters new index.
    * Steps 5a1-5a2 are repeated until index given by the user is correct.

      Use case resumes at step 6.

* 5b. The specified member is already added to the event.

    * 5b1. CCACommander shows an error message saying member is already in event.

      Use case ends.

<ins>**Use case: UC10 - Delete member from an event**</ins>

**Guarantees: MSS -> Specified member will be deleted from the event.**

**MSS**

1. User <ins>lists all members</ins> (UC03).
2. CCACommander shows a list of all members.
3. User <ins>lists all events</ins> (UC04).
4. CCACommander shows a list of all events.
5. User requests to delete a specific member in the list of all members to a specific event in the list of all events.
6. CCACommander deletes the specified member to the specified event.

    Use case ends.

**Extensions**

* 5a. The given index is invalid.

    * 5a1. CCACommander shows an error message and requests for a valid index from the user.
    * 5a2. User enters new index.
    * Steps 5a1-5a2 are repeated until index given by the user is correct.

      Use case resumes at step 6.
* 5b. The specified member is not in the event.

    * 5b1. CCACommander shows an error message saying member is not in event.

      Use case ends.

*{More to be added}*

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 members without a noticeable sluggishness in performance for typical usage.
3.  Should be able to hold up to 1000 events without a noticeable sluggishness in performance for typical usage.
4.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

*{More to be added}*

### Glossary

* **CCA**: Co-Curricular Activities
* **CLI**: Command Line Interface
* **Data archiving**: A feature related to data storage and archiving
* **Event**: An activity entity of the application
* **Extensions**: Variations or deviations from the main success scenario in a use case
* **GUI**: Graphical User Interface
* **Jar file**: A Java Archive file used to distribute and run Java applications
* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Member**: An individual entity of the application
* **MSS**: Main Success Scenario
* **Non-Functional Requirements**: Constraints under which system is developed and operated
* **PlantUML**: The tool or language used for creating diagrams in the documentation
* **Sequence Diagram**: A type of diagram used to visualize the sequence of interactions between components or objects


--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
