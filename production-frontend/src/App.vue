<script setup>
import { ref, onMounted } from "vue";

const API = "http://localhost:8080"

const rawMaterials = ref([])
const newRawMaterial = ref({ name: "", stockQuantity: 0 })

const products = ref([])
const newProduct = ref({ name: "", price: 0 })

const compositions = ref([])
const newComposition = ref({ productId: "", rawMaterialId: "", quantityRequired: 0 })

const productionPlan = ref([])

async function loadRawMaterials() {
  const response = await fetch(`${API}/raw-materials`)
  rawMaterials.value = await response.json()
}

async function createRawMaterial() {
  await fetch(`${API}/raw-materials`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(newRawMaterial.value)
  })

  newRawMaterial.value = { name: "", stockQuantity: 0 }
  loadRawMaterials()
}

async function deleteRawMaterial(id) {
  await fetch(`${API}/raw-materials/${id}`, { method: "DELETE" })
  loadRawMaterials()
  loadCompositions()

  productionPlan.value = productionPlan.value.filter(item => {
    // Verifica se todos os insumos necessários ainda existem
    const comp = compositions.value.find(c => c.product.id === item.productId);
    return comp !== undefined;
  });
}

async function loadProducts() {
  const response = await fetch(`${API}/products`)
  products.value = await response.json()
}

async function createProduct() {
  await fetch(`${API}/products`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(newProduct.value)
  })

  newProduct.value = { name: "", price: 0 }
  loadProducts()
}

async function deleteProduct(id) {
  await fetch(`${API}/products/${id}`, { method: "DELETE" })
  loadProducts()
  loadCompositions()

  productionPlan.value = productionPlan.value.filter(item =>
    products.value.some(p => p.id === item.productId)
  );
}

async function createComposition() {
  await fetch(`${API}/products-composition`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      rawMaterial: { id: newComposition.value.rawMaterialId },
      product: { id: newComposition.value.productId },
      quantityRequired: newComposition.value.quantityRequired
    })
  })

  newComposition.value = {
    productId: "",
    rawMaterialId: "",
    quantityRequired: 0
  }

  loadCompositions()
}

async function loadCompositions() {
  const response = await fetch(`${API}/products-composition`)
  compositions.value = await response.json()
}

async function loadProductionPlan() {
  const response = await fetch(`${API}/production-plan`)
  productionPlan.value = await response.json()
}

onMounted(() => {
  loadProducts()
  loadRawMaterials()
  loadCompositions()
})

</script>

<template>
  <div>

    <h1>Raw Materials</h1>

    <p>Name and Stock Quantity</p>
    <input v-model="newRawMaterial.name" placeholder="Name" />
    <input v-model.number="newRawMaterial.stockQuantity" type="number" placeholder="Stock" />
    <button @click="createRawMaterial">Add</button>

    <ul>
      <li v-for="rm in rawMaterials" :key="rm.id">
        {{ rm.name }} - {{ rm.stockQuantity }}
        <button @click="deleteRawMaterial(rm.id)">Delete</button>
      </li>
    </ul>

    <hr />

    <h1>Products</h1>

    <p>Name and Price</p>
    <input v-model="newProduct.name" placeholder="Name" />
    <input v-model.number="newProduct.price" type="number" placeholder="Price" />
    <button @click="createProduct">Add</button>

    <ul>
      <li v-for="product in products" :key="product.id">
        {{ product.name }} - {{ product.price }}
        <button @click="deleteProduct(product.id)">Delete</button>
      </li>
    </ul>

    <hr />

    <h1>Product Composition</h1>

    <p>Product, Raw Material and Quantity Required to Produce</p>
    <select v-model="newComposition.productId">
      <option disabled value="">Select Product</option>
      <option v-for="p in products" :key="p.id" :value="p.id">
        {{ p.name }}
      </option>
    </select>

    <select v-model="newComposition.rawMaterialId">
      <option disabled value="">Select Raw Material</option>
      <option v-for="rm in rawMaterials" :key="rm.id" :value="rm.id">
        {{ rm.name }}
      </option>
    </select>

    <input v-model.number="newComposition.quantityRequired" type="number" placeholder="Quantity Required" />

    <button @click="createComposition">
      Add Composition
    </button>

    <h3>Registered Compositions</h3>

    <ul>
      <li v-for="comp in compositions" :key="comp.id">
        {{ comp.product.name }}
        requires
        {{ comp.quantityRequired }}
        of
        {{ comp.rawMaterial.name }}
      </li>
    </ul>



    <hr>

    <h1>Production Plan</h1>

    <button @click="loadProductionPlan">
      Calculate Production
    </button>

    <ul v-if="productionPlan.length > 0">
      <li v-for="item in productionPlan" :key="item.productName">
        {{ item.productName }}
        | Quantity: {{ item.quantityToProduce }}
        | Total: {{ item.totalValue }}
      </li>
    </ul>

    <p v-else>No production possible.</p>

  </div>

</template>

<style>

div{
  background-color: #e0f2ff;
  font-family: Arial, sans-serif;
}

</style>
