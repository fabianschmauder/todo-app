import React from "react";
import {TodoDispatchContext, TodoStateContext} from "./TodoContext";

export const useTodoState = () => {
    const context = React.useContext(TodoStateContext);
    if (context === undefined) {
        throw new Error('useTodoState must be used within a TodoContextProvider')
    }
    return context
};

export const useTodoDispatch = () => {
    const context = React.useContext(TodoDispatchContext);
    if (context === undefined) {
        throw new Error('useTodoDispatch must be used within a TodoContextProvider')
    }
    return context
};
