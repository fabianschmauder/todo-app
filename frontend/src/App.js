import React from 'react';
import './App.css';
import {TodoView} from "./components/todo-view/TodoView";
import {TodoContextProvider} from "./context/todo/TodoContext";

function App() {

    return (
        <TodoContextProvider>
            <TodoView/>
        </TodoContextProvider>
    );
}

export default App;
