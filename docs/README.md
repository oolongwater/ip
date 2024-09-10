# Giorgo User Guide

![screenshot](Ui.png)

## Introduction
Giorgo is a task management chatbot that helps you keep track of your tasks, deadlines, and events. You can interact with Giorgo using simple text commands.

## Features

### Adding Tasks
- **Todo**: Adds a todo task.
    - **Command**: `todo <description>, <priority>`
    - **Example**: `todo read book, 1`
    - **Error**: If the description is empty, Giorgo will respond with "todo cannot be empty!"

- **Deadline**: Adds a task with a deadline.
    - **Command**: `deadline <description> /by <date>, <priority>`
    - **Example**: `deadline submit report /by 12/12/2023 1800, 1`
    - **Error**: If the date is invalid, Giorgo will respond with an appropriate error message.

- **Event**: Adds an event with a start and end time.
    - **Command**: `event <description> /from <start> /to <end>, <priority>`
    - **Example**: `event project meeting /from 12/12/2023 1400 /to 12/12/2023 1600, 1`

### Listing Tasks
- **List**: Lists all tasks.
    - **Command**: `list`
    - **Example**: `list`

### Marking Tasks
- **Mark**: Marks a task as done.
    - **Command**: `mark <index>`
    - **Example**: `mark 1`
    - **Error**: If the index is out of bounds, Giorgo will respond with "there are only [number of tasks] tasks in the list!"

- **Unmark**: Unmarks a task.
    - **Command**: `unmark <index>`
    - **Example**: `unmark 1`
    - **Error**: If the index is out of bounds, Giorgo will respond with "there are only [number of tasks] tasks in the list!"

### Deleting Tasks
- **Delete**: Deletes a task.
    - **Command**: `delete <index>`
    - **Example**: `delete 1`
    - **Error**: If the index is out of bounds, Giorgo will respond with "UH-OH! There are only [number of tasks] tasks in the list!"

### Finding Tasks
- **Find**: Finds tasks that match a keyword.
    - **Command**: `find <keyword>`
    - **Example**: `find book`
    - **Error**: If no tasks are found, Giorgo will respond with "No tasks found matching [keyword]"

### Viewing Tasks by Date
- **Date**: Lists tasks on a specific date _(dd / mm / yyyy)_.
    - **Command**: `date <date>`
    - **Example**: `date 12/12/2023`
    - **Error**: If no tasks are found, Giorgo will respond with "No tasks found on [date]"

### Exiting
- **Bye**: Exits the chatbot.
    - **Command**: `bye`
    - **Example**: `bye`

## Conclusion
Giorgo is designed to make task management simple and efficient. Use the commands above to interact with Giorgo and manage your tasks effectively.