import numpy as np
import matplotlib.pyplot as plt

# Example: 16 arbitrary integers (replace with actual values)
y = [5, 12, 23, 45, 50, 65, 78, 80, 90, 95, 100, 110, 120, 135, 140, 150]

# Generate x-values (just sequential indices)
x = np.arange(len(y))  # x = [0, 1, 2, ..., 15]

# Create figure and axis
fig, ax = plt.subplots()

# Plot the line through all points
ax.plot(x, y, linestyle='solid', color='blue', marker='o', label="Data Line")

# Customize labels and title
ax.set_xlabel("Index")
ax.set_ylabel("Value")
ax.set_title("Line Chart Through 16 Arbitrary Points")
ax.legend()

# Show the plot
plt.show()