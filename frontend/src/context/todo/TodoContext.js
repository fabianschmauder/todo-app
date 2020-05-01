import React from "react";
import {todoContextReducer} from "./todo-reducer";

export const TodoStateContext = React.createContext(undefined);
export const TodoDispatchContext = React.createContext(undefined);

export const TodoContextProvider = ({children}) => {
    const [state, dispatch] = React.useReducer(todoContextReducer, {
        todos: []
    });
    return <TodoStateContext.Provider value={state}>
        <TodoDispatchContext.Provider value={dispatch}>
            {children}
        </TodoDispatchContext.Provider>
    </TodoStateContext.Provider>
};
