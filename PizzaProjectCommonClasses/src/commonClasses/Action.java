/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package commonClasses;

/**
 *
 * @author PC
 */
public class Action {
   ActionType actionType;
   String actionParamsJSON;
 

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public String getActionParamsJSON() {
        return actionParamsJSON;
    }

    public void setActionParamsJSON(String actionParamsJSON) {
        this.actionParamsJSON = actionParamsJSON;
    }
   
}
