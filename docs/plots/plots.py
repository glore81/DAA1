import pandas as pd
import matplotlib.pyplot as plt
import os

CSV_PATH = r"C:\Users\foove\IdeaProjects\DAA1\results\results.csv"
SCREENSHOTS_DIR = r"C:\Users\foove\IdeaProjects\DAA1\docs\screenshots"

os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

try:
    df = pd.read_csv(CSV_PATH, skipinitialspace=True)
except FileNotFoundError:
    print(f"Error: {CSV_PATH} not found. Run the Java program first!")
    exit()

plt.style.use('ggplot')

random_sorts = df[(df['InputType'] == 'Random') & (df['Algorithm'].isin(['MergeSort', 'QuickSort']))]

plt.figure(figsize=(10, 6))
for alg in ['MergeSort', 'QuickSort']:
    alg_data = random_sorts[random_sorts['Algorithm'] == alg]
    plt.plot(alg_data['Size'], alg_data['ExecutionTimeMs'], marker='o', linewidth=2, label=alg)

plt.title('MergeSort vs QuickSort (Random Input): Time vs. Size')
plt.xlabel('Input Size (n)')
plt.ylabel('Execution Time (ms)')
plt.legend()
plt.grid(True)
plt.savefig(os.path.join(SCREENSHOTS_DIR, '1_sorting_time_vs_n.png'), bbox_inches='tight')
plt.close()

plt.figure(figsize=(10, 6))
for alg in ['MergeSort', 'QuickSort']:
    alg_data = random_sorts[random_sorts['Algorithm'] == alg]
    plt.plot(alg_data['Size'], alg_data['MaxDepth'], marker='s', linewidth=2, label=alg)

plt.title('MergeSort vs QuickSort (Random Input): Recursion Depth vs. Size')
plt.xlabel('Input Size (n)')
plt.ylabel('Max Recursion Depth')
plt.legend()
plt.grid(True)
plt.savefig(os.path.join(SCREENSHOTS_DIR, '2_recursion_depth_vs_n.png'), bbox_inches='tight')
plt.close()

plt.figure(figsize=(10, 6))
quick_sort = df[df['Algorithm'] == 'QuickSort']

for input_type in ['Random', 'Sorted', 'ReverseSorted', 'Duplicates']:
    type_data = quick_sort[quick_sort['InputType'] == input_type]
    plt.plot(type_data['Size'], type_data['ExecutionTimeMs'], marker='^', linewidth=2, label=input_type)

plt.title('QuickSort on Different Inputs: Time vs. Size')
plt.xlabel('Input Size (n)')
plt.ylabel('Execution Time (ms)')
plt.yscale('log')
plt.legend()
plt.grid(True)
plt.savefig(os.path.join(SCREENSHOTS_DIR, '3_quicksort_inputs.png'), bbox_inches='tight')
plt.close()

plt.figure(figsize=(10, 6))
select_data = df[df['Algorithm'] == 'DeterministicSelector']
closest_data = df[df['Algorithm'] == 'ClosestPair']

plt.plot(select_data['Size'], select_data['ExecutionTimeMs'], marker='o', linewidth=2, label='Deterministic Select', color='green')
plt.plot(closest_data['Size'], closest_data['ExecutionTimeMs'], marker='X', linewidth=2, label='Closest Pair', color='purple')

plt.title('Deterministic Select & Closest Pair: Time vs. Size')
plt.xlabel('Input Size (n)')
plt.ylabel('Execution Time (ms)')
plt.legend()
plt.grid(True)
plt.savefig(os.path.join(SCREENSHOTS_DIR, '4_other_algorithms.png'), bbox_inches='tight')
plt.close()

print(f"Done! 4 plots have been successfully saved to {SCREENSHOTS_DIR}")