import tkinter as tk
from tkinter import messagebox

def on_button_click():
    messagebox.showinfo("Info", "Button clicked!")

root = tk.Tk()
root.title("Tkinter Example")
root.geometry("300x200")

button = tk.Button(root, text="Click Me second!", command=on_button_click)
poop = tk.Button(root, text="Click Me first!", command=root.destroy)
poop.pack(padx=50)
button.pack(pady=20)

root.mainloop()
