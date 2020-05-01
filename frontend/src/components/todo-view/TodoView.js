import React, {useEffect, useState} from "react";
import {Button} from '@material-ui/core';
import {AddTodoDialog} from "../dialog/AddTodoDialog";
import {TodoPanel} from "./TodoPanel";
import {addItem, fetchItems} from "../../context/todo/todo-actions";
import {useTodoDispatch, useTodoState} from "../../context/todo/todo-selector";

export const TodoView = () => {
    const {todos} = useTodoState();
    const [openAddDialog, setOpenAddDialog] = useState(false);

    const dispatch = useTodoDispatch();
    useEffect(() => {
        fetchItems(dispatch);
    }, [dispatch]);

    return <div style={{display: 'flex', flexDirection: 'column', height: '100%'}}>
        <Button color="primary" onClick={() => setOpenAddDialog(true)}>Add</Button>
        <div style={{display: 'flex', flexGrow: 1}}>
            <TodoPanel items={todos} status={'OPEN'}/>
            <TodoPanel items={todos} status={'IN_PROGRESS'}/>
            <TodoPanel items={todos} status={'DONE'}/>
        </div>
        {openAddDialog &&
        <AddTodoDialog open={openAddDialog} onClose={() => setOpenAddDialog(false)} onAdd={async (description) => {
            setOpenAddDialog(false);
            await addItem(dispatch, description);
        }}/>}
    </div>
};
